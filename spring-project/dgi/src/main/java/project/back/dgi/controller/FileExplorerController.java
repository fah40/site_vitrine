package project.back.dgi.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileExplorerController {

    @Autowired
    private ServletContext servletContext;

    @Value("${app.storage.location}") // Injection du chemin défini dans application.properties
    private String storagePath;

    @GetMapping("/explorer")
    public String listFiles(@RequestParam(required = false, defaultValue = "") String path, Model model, HttpServletResponse response) throws IOException {
        File fileOrDirectory = new File(storagePath + File.separator + path);
        System.out.println("Chemin utilisé : " + fileOrDirectory.getAbsolutePath());

        if (!fileOrDirectory.exists()) {
            throw new RuntimeException("Le chemin spécifié n'existe pas.");
        }

        // Si c'est un fichier, on déclenche le téléchargement
        if (fileOrDirectory.isFile()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileOrDirectory.getName() + "\"");
            Files.copy(fileOrDirectory.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
            return null; // Empêche Thymeleaf de traiter une vue
        }

        // Si c'est un dossier, on affiche la liste des fichiers
        File[] filesArray = fileOrDirectory.listFiles();
        if (filesArray == null) {
            throw new RuntimeException("Impossible de lister les fichiers dans le dossier.");
        }

        List<String> files = Arrays.stream(filesArray)
                .map(File::getName)
                .collect(Collectors.toList());

        model.addAttribute("files", files);
        model.addAttribute("currentPath", path);
        return "explorer"; // Retourne la vue Thymeleaf
    }



    @PostMapping("/create-folder")
    public String createFolder(@RequestParam String path, @RequestParam String folderName) {
        // Construire le chemin absolu du dossier parent
        File parentDir = new File(storagePath + File.separator + path);
        if (!parentDir.exists()) {
            parentDir.mkdirs(); // Crée le dossier parent s'il n'existe pas
        }

        // Créer le dossier demandé
        File newFolder = new File(parentDir, folderName);
        if (!newFolder.exists()) {
            boolean created = newFolder.mkdirs();
            if (!created) {
                throw new RuntimeException("Impossible de créer le dossier : " + newFolder.getAbsolutePath());
            }
        }

        return "redirect:/explorer?path=" + path;
    }

    @PostMapping("/delete")
    public String deleteFile(@RequestParam String path, @RequestParam String fileName) {
        // Construire le chemin absolu
        File fileToDelete = new File(storagePath + File.separator + path, fileName);

        if (!fileToDelete.exists()) {
            throw new RuntimeException("Le fichier/dossier n'existe pas : " + fileToDelete.getAbsolutePath());
        }

        if (fileToDelete.isDirectory()) {
            // Supprime le dossier et son contenu récursivement
            deleteDirectory(fileToDelete);
        } else {
            // Supprime un fichier simple
            if (!fileToDelete.delete()) {
                throw new RuntimeException("Impossible de supprimer le fichier.");
            }
        }

        return "redirect:/explorer?path=" + path;
    }

    private void deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam String path, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide !");
        }

        try {
            // Emplacement où stocker les fichiers
            String uploadDir = storagePath ;

            // Création du chemin du dossier si `path` est fourni
            File destinationFolder = new File(uploadDir + File.separator + path);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs(); // Crée tous les dossiers nécessaires
            }

            // Nom du fichier avec timestamp pour éviter les conflits
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Emplacement final du fichier
            Path filePath = Paths.get(destinationFolder.getAbsolutePath(), fileName);

            // Sauvegarde du fichier
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
        }

        return "redirect:/explorer?path=" + path;
    }


}