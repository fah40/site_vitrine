package project.back.dgi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/admin")
public class PopupController {

    @Value("${file.popup-upload-dir}")
    private String uploadPath;

    @Value("${file.dg-image-dir}")
    private String dgImagePath;

    @PostMapping("/uploadPopupImage")
    public String handleFileUpload(@RequestParam("fileInput") MultipartFile file) {
        if (file.isEmpty()) {
            // Gérer le cas où aucun fichier n'est sélectionné
            return "redirect:/admin/accueil";
        }

        // Vérifier si le dossier d'upload existe, sinon le créer
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Supprimer tous les fichiers existants dans le répertoire
        File[] existingFiles = uploadDir.listFiles();
        if (existingFiles != null) {
            for (File existingFile : existingFiles) {
                if (existingFile.isFile()) {
                    existingFile.delete();
                }
            }
        }

        // Enregistrer le nouveau fichier avec son nom d'origine
        try {
            String fileName = file.getOriginalFilename(); // Conserver le nom d'origine du fichier
            Path filePath = Paths.get(uploadPath, fileName);
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/accueil";
        }

        return "redirect:/admin/accueil";
    }

    @PostMapping("/uploadDgImage")
    public String uploadDgImg(@RequestParam("fileInput") MultipartFile file) {
        if (file.isEmpty()) {
            // Gérer le cas où aucun fichier n'est sélectionné
            return "redirect:/admin/accueil";
        }

        // Vérifier si le dossier d'upload existe, sinon le créer
        File uploadDir = new File(dgImagePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Supprimer tous les fichiers existants dans le répertoire
        File[] existingFiles = uploadDir.listFiles();
        if (existingFiles != null) {
            for (File existingFile : existingFiles) {
                if (existingFile.isFile()) {
                    existingFile.delete();
                }
            }
        }

        // Enregistrer le nouveau fichier avec son nom d'origine
        try {
            String fileName = file.getOriginalFilename(); // Conserver le nom d'origine du fichier
            Path filePath = Paths.get(dgImagePath, fileName);
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/accueil";
        }

        return "redirect:/admin/accueil";
    }

    @GetMapping("/deletePopup")
    public String delete () {
        // Vérifier si le dossier d'upload existe, sinon le créer
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Supprimer tous les fichiers existants dans le répertoire
        File[] existingFiles = uploadDir.listFiles();
        if (existingFiles != null) {
            for (File existingFile : existingFiles) {
                if (existingFile.isFile()) {
                    existingFile.delete();
                }
            }
        }

        return "redirect:/admin/accueil";
    }
    
}