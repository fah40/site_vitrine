package project.back.dgi.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import project.back.dgi.entity.Attempt;
import project.back.dgi.entity.User;
import project.back.dgi.entity.UserPin;
import project.back.dgi.repository.AttemptRepository;
import project.back.dgi.repository.UserPinRepository;
import project.back.dgi.service.ConfigurationService;
import project.back.dgi.service.UserService;
import project.back.dgi.util.Constante;
import project.back.dgi.util.EmailUtil;
import project.back.dgi.util.HCaptchaUtil;
import project.back.dgi.util.PasswordUtil;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private UserService userService; // Utilisation de UserService
    
    @Autowired
    private UserPinRepository userPinRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private ConfigurationService configurationService;


    /**
     * Affiche la page de connexion (email)
     */
    @GetMapping("/login")
    public String loginPage() {
        return "pagLogin/login";
    }

    /**
     * Vérification de l'email (1ère étape)
     */
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam(name =  "h-captcha-response", required = false) String hCaptchaResponse,
                        Model model,
                        HttpSession session) {
        if ( (hCaptchaResponse==null || hCaptchaResponse.isEmpty()) || (!HCaptchaUtil.verifyHCaptcha(hCaptchaResponse))) {
            model.addAttribute("error", "Vérification hCaptcha échouée !");
            return "pagLogin/login";
        }

        User user = userService.findByEmail(email).orElse(null);

        // Génération du PIN
        String pinGenerated = PasswordUtil.generatePin(Integer.parseInt(configurationService.getValueByKey("pin_length")));

        // Création de l'objet UserPin
        UserPin userPin = new UserPin();
        userPin.setPin(pinGenerated);
        userPin.setCreationDate(LocalDateTime.now());
        userPin.setExpirationDate(LocalDateTime.now().plusMinutes(Long.parseLong(configurationService.getValueByKey("pin_expiration_minute"))));
        userPin.setUser(user);

        // Sauvegarde du PIN en base de données
        userPinRepository.save(userPin);

        // Envoi de l'email (optionnel, si tu as une méthode pour cela)
        emailUtil.sendHtmlEmail(user.getEmail(),configurationService.getValueByKey("sending_email_pin_content"),pinGenerated);

        model.addAttribute("email", email);
        // savoir qu'il a franchi la premiere etape 
        session.setAttribute("from", "1");
        return "pagLogin/login2";
    }

    /**
     * Vérification du PIN (2ème étape)
     */
    @PostMapping("/authenticate2")
    public String authenticate2(@RequestParam String email, 
                                @RequestParam String pin, 
                                Model model,
                                HttpSession session) {
        // verifier qu'il a franchi la premiere etape 
        if (((String) session.getAttribute("from")).compareTo("1") != 0) {
            model.addAttribute("error", "passez par notre captcha!");
        }
        // Recherche de l'utilisateur par email
        User user = userService.findByEmail(email).orElse(null);

        // Récupère le dernier PIN de l'utilisateur
        Optional<UserPin> lastUserPin = userPinRepository.findByUser(user).stream()
                .max(Comparator.comparing(UserPin::getCreationDate));  // On prend le dernier PIN

        if (!lastUserPin.isPresent()) {
            model.addAttribute("error", "Aucun PIN trouvé. Log pour en demander un nouveau.");
            return "pagLogin/login";
        }

        UserPin userPin = lastUserPin.get();

        // Vérifie si le PIN est obsolète (expiration passée)
        if (userPin.getExpirationDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Le PIN est obsolète. Veuillez en demander un nouveau.");
            return "pagLogin/login";
        }

        // Vérifie si le PIN est correct
        if (!pin.equals(userPin.getPin())) {
            PasswordUtil.waitError();  // Vous pouvez définir un délai d'attente ici si nécessaire
            model.addAttribute("email", email);
            model.addAttribute("error", "Le PIN est incorrect ou obsolète. Vérifiez le dernier dans votre email.");
            return "pagLogin/login2";
        }

        // Si le PIN est correct, redirige vers la page suivante
        model.addAttribute("email", email);
        // savoir qu'il a franchi la 2 etape 
        session.setAttribute("from", "2");
        return "pagLogin/login3"; // Redirige vers la page de saisie du mot de passe
    }

    @GetMapping("/authenticate2")
    public String authenticate2_get (){
        return "redirect:/auth/login";
    }


    /**
     * Vérification du mot de passe (3ème étape)
     */
    @PostMapping("/authenticate3")
    public String authenticate3(@RequestParam String email, 
                            @RequestParam String password, 
                            HttpServletResponse response,
                            Model model,
                            HttpSession session) throws IOException {
        // verifier qu'il a franchi la 2 eme etape 
        if (((String) session.getAttribute("from")).compareTo("1") != 0) {
            model.addAttribute("error", "passez par notre pin!");
        }
        // Recherche de l'utilisateur par email
        User user = userService.findByEmail(email).orElse(null);
        // Récupère les tentatives de l'utilisateur
        Attempt attempt = userService.getAttempt(user);
        
        // Vérification du mot de passe
        boolean passwordMatch = PasswordUtil.compareCrypt(password, user.getPassword());
        if (!passwordMatch) {
            // Si le mot de passe est incorrect, on incrémente le compteur de tentatives
            attempt.setCountAttempt(attempt.getCountAttempt() + 1);
            String next_attempt_minute = configurationService.getValueByKey("next_attempt_minute");
            LocalDateTime nextAttemptTime = LocalDateTime.now().plusMinutes(Long.parseLong(next_attempt_minute));
            attempt.setDateNextAttempt(LocalDateTime.now().plusMinutes(Long.parseLong(next_attempt_minute))); // Définir la prochaine tentative à 10 minutes après
            attemptRepository.save(attempt);

            // Si le nombre d'essais atteint le seuil
            if (attempt.getCountAttempt() >= Integer.parseInt(configurationService.getValueByKey("count_attempt"))) {
                // Calcul du délai restant en minutes et secondes
                long secondsRemaining = java.time.Duration.between(LocalDateTime.now(), nextAttemptTime).getSeconds();
                long minutesRemaining = secondsRemaining / 60;
                long secondsRemainingAfterMinute = secondsRemaining % 60;

                // Calcul du message d'erreur
                String errorMessage = "Tentatives échouées.";
                String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

                // Rediriger vers la page login avec les paramètres
                response.sendRedirect("/auth/login?error=" + encodedMessage + "&minute=" + minutesRemaining + "&seconds=" + secondsRemainingAfterMinute);
                return null; // Ne pas retourner de vue car on fait une redirection
        }

            // Si le mot de passe est incorrect mais il n'est pas à la x ième tentative, on le renvoie à login3
            model.addAttribute("email", email);
            model.addAttribute("error", "Mot de passe incorrect !");
            return "pagLogin/login3";
        }

        // Si le mot de passe est correct, on réinitialise les tentatives et on redirige vers l'accueil
        attempt.setCountAttempt(0); // Réinitialise le compteur de tentatives
        attempt.setDateNextAttempt(null); // Réinitialise la date de prochaine tentative
        attemptRepository.save(attempt);

        model.addAttribute("email", email);
        session.setAttribute("user", user);
        Timestamp expiration = new Timestamp(System.currentTimeMillis() + Constante.getSessionDuration() * 60 * 60 * 1000);
        session.setAttribute("expiration", expiration);

        session.removeAttribute("from");

        return "redirect:/admin/accueil"; // Redirige vers la page d'accueil
    }

    @GetMapping("/authenticate3")
    public String authenticate3_get (){
        return "redirect:/auth/login";
    }
}
