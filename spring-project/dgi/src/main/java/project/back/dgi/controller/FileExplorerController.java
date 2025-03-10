package project.back.dgi.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.service.GeneralInfoService;
import project.back.dgi.service.LangueService;

@Controller
@RequestMapping("/admin")
public class FileExplorerController {

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private GeneralInfoService generalInfoService;
    @Autowired
    private LangueService langueService;


    @Value("${app.storage.location}") // Injection du chemin défini dans application.properties
    private String storagePath;

    @Value("${file.upload-dir}")
    private String uploadPath;

    @GetMapping("/explorer")
    public String listFiles(@RequestParam(required = false, defaultValue = "") String path,@RequestParam(required = false, defaultValue = "2") String langue , Model model, HttpServletResponse response) throws IOException {
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
        
        HashMap<String, GeneralInfo> generalInfoMap = new HashMap<>();
        HashMap<String, GeneralInfo> generalInfoFolderMap = new HashMap<>();
        HashMap<String, GeneralInfoValeur> generalInfoValueMap = new HashMap<>();

        String descri = path.startsWith("/") ? path : "/" + path;
        GeneralInfoValeur giv = generalInfoService.getGeneralInfoValeurByKeyAndLanguage(generalInfoService.getByCle(descri).orElse(null), langueService.findById(Long.parseLong(langue)));
        descri = giv == null ? "" : giv.getValeur();
        String tempPath = new String(path);
        
        for (String file : files) {
            File relatedFile = new File(storagePath + File.separator + file);
            
            String key = '/' + file;
            GeneralInfo generalInfo = generalInfoService.getByCle('/' + file).orElse(null);

            if (!tempPath.isEmpty()) {
                generalInfo = generalInfoService.getByCle(tempPath + '/' + file).orElse(null);
                key = tempPath + file;
            }

            System.out.println("Clé : " + key);
            
            if (generalInfo != null) {
                System.out.println("file : " + file);

                GeneralInfoValeur generalInfoValeur = generalInfoService.getGeneralInfoValeurByKeyAndLanguage(generalInfo, langueService.findById(Long.parseLong(langue)));
                generalInfoValueMap.put(file, generalInfoValeur);    
                generalInfoFolderMap.put(file, generalInfo);
            
            } else {
                if (!relatedFile.isDirectory()) {
                    System.out.println("Is actually a file : " + file);
                    
                    generalInfoMap.put(file, generalInfo);
                }
            }
        }
        
        // au cas ou vide
        // if (files.isEmpty()) {
            //     return "dossier-vide";
        // }

        model.addAttribute("files", files);
        model.addAttribute("mapFiles", generalInfoMap);
        model.addAttribute("mapFolders", generalInfoFolderMap);
        model.addAttribute("mapValueFolders", generalInfoFolderMap);
        model.addAttribute("descri", descri);
        model.addAttribute("currentPath", path);
        
        System.out.println("SIZEEEEEEEEEEEEEEEEEEEEE : " + generalInfoFolderMap.size());
        
        return "explorerUpdate"; // Retourne la vue Thymeleaf
    }



    @PostMapping("/create-folder")
    public String createFolder(@RequestParam String path, @RequestParam String folderName, @RequestParam(required = false, defaultValue = "") String lien) {
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

        GeneralInfo generalInfo = new GeneralInfo();

        generalInfo.setCle(path + '/' + folderName);
        if (lien.isEmpty()) {
            generalInfo.setLien("/admin/explorer?path=" + path + '/' + folderName);
        } else {
            generalInfo.setLien(lien);
        }

        // pour initialiser les dossiers en base
        // GeneralInfo inBase = generalInfoService.getByCle(folderName).orElse(null);
        // if (inBase != null) {
        //     inBase.setLien(lien);
        //     generalInfoService.save(inBase);
        // }else{
            generalInfoService.save(generalInfo);
        // }


        return "redirect:/admin/explorer?path=" + path;
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

        return "redirect:/admin/explorer?path=" + path;
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
    public String uploadFiles(@RequestParam String path, @RequestParam("file") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new RuntimeException("Aucun fichier n'a été sélectionné !");
        }

        try {
            // Emplacement où stocker les fichiers
            String uploadDir = storagePath;

            // Création du chemin du dossier si `path` est fourni
            File destinationFolder = new File(uploadDir + File.separator + path);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs(); // Crée tous les dossiers nécessaires
            }

            // Parcourir chaque fichier et le sauvegarder
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue; // Ignorer les fichiers vides
                }

                // Nom du fichier avec timestamp pour éviter les conflits
                String fileName = file.getOriginalFilename();

                // Emplacement final du fichier
                Path filePath = Paths.get(destinationFolder.getAbsolutePath(), fileName);

                // Sauvegarde du fichier
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement des fichiers : " + e.getMessage());
        }

        return "redirect:/admin/explorer?path=" + path;
    }
}