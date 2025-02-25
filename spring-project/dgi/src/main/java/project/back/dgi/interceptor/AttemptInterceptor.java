package project.back.dgi.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import project.back.dgi.entity.Attempt;
import project.back.dgi.entity.User;
import project.back.dgi.repository.AttemptRepository;
import project.back.dgi.service.ConfigurationService;
import project.back.dgi.service.UserService;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class AttemptInterceptor implements HandlerInterceptor {

    @Autowired
    private AttemptRepository attemptRepository;

    private final UserService userService;

    @Autowired
    private ConfigurationService configurationService;

    public AttemptInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String email = request.getParameter("email");
        if (email != null) {
            User user = userService.findByEmail(email).orElse(null);
            if (user != null) {
                Attempt attempt = userService.getAttempt(user);
                if (attempt != null && attempt.getCountAttempt() >= Integer.parseInt(configurationService.getValueByKey("count_attempt"))) {
                    LocalDateTime timeUntilNextAttempt = attempt.getDateNextAttempt().minusSeconds(LocalDateTime.now().getSecond());
                    if (timeUntilNextAttempt.isAfter(LocalDateTime.now())) {
                        // Calcul de la durée restante avant la prochaine tentative
                        Duration duration = Duration.between(LocalDateTime.now(), timeUntilNextAttempt);
                        
                        // Obtention des minutes et secondes restantes
                        long minutes = duration.toMinutes();
                        long seconds = duration.getSeconds() % 60;
                    
                        // Préparation du message d'erreur
                        String errorMessage = "Tentatives échouées.";
                        String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                        
                        // Redirection avec les paramètres minutes et seconds dans l'URL
                        response.sendRedirect("/auth/login?error=" + encodedMessage + "&minute=" + minutes + "&seconds=" + seconds);
                        return false; // Empêche l'exécution du handler si trop de tentatives
                    }
                    attempt.setCountAttempt(0);
                    attempt.setDateNextAttempt(null);
                    attemptRepository.save(attempt);
                }
            }else{
                response.sendRedirect("/auth/login?error=Utilisateur inexistant.");
                return false;
            }
        }
        return true; // Permet la suite de la logique
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Optionnel : post-traitement après l'exécution du contrôleur
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Optionnel : après la complétion de la requête
    }
}
