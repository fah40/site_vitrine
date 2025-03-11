package project.back.dgi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import project.back.dgi.entity.Actualite;
import project.back.dgi.entity.PieceJointe;
import project.back.dgi.entity.User;
import project.back.dgi.service.ActualiteService;
import project.back.dgi.service.PieceJointeService;
import project.back.dgi.service.UserService;

@Controller
@RequestMapping("/admin")
public class ActualiteController {

    @Autowired
    private ActualiteService actualiteService;

    @Autowired
    private PieceJointeService pieceJointeService;

    @Autowired
    private UserService userService;

    @Value("${file.upload-dir}")
    private String storagePath;

    // Afficher le formulaire d'ajout d'une actualité
    @GetMapping("/actualites/add")
    public String showAddForm(Model model) {
        model.addAttribute("actualite", new Actualite());
        return "actualite-form"; // Nom du template Thymeleaf
    }


    @PostMapping("/actualites/save")
    public String saveActualite(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam("files") MultipartFile[] files) {

        // Créer une nouvelle actualité
        Actualite actualite = new Actualite();

        // Récupérer l'utilisateur (simulé ici, à remplacer par votre logique)
        User user = userService.findByEmail("fanasinamanantsoa30@gmail.com").orElse(null);

        // Définir la date d'ajout
        actualite.setDateAjout(Timestamp.from(Instant.now()));

        // Définir le titre et la description
        actualite.setTitre(title);
        actualite.setDescription(content);

        // Associer l'utilisateur à l'actualité
        actualite.setUser(user);

        // Enregistrer l'actualité
        actualite = actualiteService.saveActualite(actualite);

        // Gérer l'upload des fichiers
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        // Créer le dossier de stockage si nécessaire
                        File uploadDir = new File(storagePath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        // Générer un nom de fichier unique
                        String fileName = file.getOriginalFilename();

                        // Chemin complet du fichier
                        Path filePath = Paths.get(uploadDir.getAbsolutePath(), fileName);

                        // Sauvegarder le fichier
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        // Créer une nouvelle pièce jointe
                        PieceJointe pieceJointe = new PieceJointe();

                        // Définir l'URL du fichier et le nom du fichier
                        pieceJointe.setUrlFichier(filePath.toString());
                        pieceJointe.setNomFichier(file.getOriginalFilename());

                        // Définir l'actualité associée
                        pieceJointe.setActualite(actualite);

                        // Vérifier si le fichier est une image
                        boolean isImage = fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                                        fileName.endsWith(".png") || fileName.endsWith(".gif") ||
                                        fileName.endsWith(".bmp") || fileName.endsWith(".webp");

                        // Définir isImage en fonction du type de fichier
                        pieceJointe.setImage(isImage);

                        // Enregistrer la pièce jointe
                        pieceJointeService.savePieceJointe(pieceJointe);

                    } catch (IOException e) {
                        throw new RuntimeException("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
                    }
                }
            }
        }

        return "redirect:/admin/actualites/add"; // Rediriger vers le formulaire
    }

    @GetMapping("/actualite/{idActu}")    
    public String getActuDetails (@PathVariable long idActu, Model model) {
        Actualite actualite = actualiteService.getActualiteById(idActu).orElse(null);
        List<PieceJointe> pieceJointes = pieceJointeService.getPiecesJointesNotImageByActualiteId(idActu);

        if (actualite != null) {
            model.addAttribute("actualite", actualite);
            model.addAttribute("pieceJointes", pieceJointes);
            
            return "detail_actu";
        }

        return "redirect:/admin/accueil";
    }
}