package project.back.dgi.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.service.GeneralInfoService;
import project.back.dgi.service.LangueService;

@Controller
public class ClientFileExplorerController {

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
        long id_langue = 2;

        try {
            id_langue = Long.parseLong(langue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id_langue > 3 || id_langue < 1) {
            id_langue = 2;
        }

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
        GeneralInfoValeur giv = generalInfoService.getGeneralInfoValeurByKeyAndLanguage(generalInfoService.getByCle(descri).orElse(null), langueService.findById(id_langue));
        descri = giv == null ? "" : giv.getValeur();
        String tempPath = new String(path);

        for (String file : files) {
            File relatedFile = new File(storagePath + File.separator + file);
            
            String key = '/' + file;
            GeneralInfo generalInfo = generalInfoService.getByCle('/' + file).orElse(null);
            if (!tempPath.isEmpty()) {
                generalInfo = generalInfoService.getByCle(tempPath + '/' + file).orElse(null);
                key = tempPath + '/' + file;
            }

            if (generalInfo != null) {

                GeneralInfoValeur generalInfoValeur = generalInfoService.getGeneralInfoValeurByKeyAndLanguage(generalInfo, langueService.findById(Long.parseLong(langue)));
                if (generalInfo.getLien().startsWith("/admin/")) {
                    generalInfo.setLien(generalInfo.getLien().substring(generalInfo.getLien().indexOf("/explorer")));
                }

                if (generalInfoValeur.getBouton().isEmpty()) {
                    generalInfoValeur.setBouton(file);
                }

                generalInfoValueMap.put(file, generalInfoValeur);    
                generalInfoFolderMap.put(file, generalInfo);
            } else {
                if (!relatedFile.isDirectory()) {
                    generalInfoMap.put(file, generalInfo);
                }
            }
        }

        // au cas ou vide
        if (files.isEmpty()) {
            return "dossier-vide";
        }

        model.addAttribute("files", files);
        model.addAttribute("mapFiles", generalInfoMap);
        model.addAttribute("mapFolders", generalInfoFolderMap);
        model.addAttribute("mapValueFolders", generalInfoValueMap);
        model.addAttribute("descri", descri);
        model.addAttribute("currentPath", path);
        model.addAttribute("mainGeneralInfo", giv);
        return "explorer-client"; // Retourne la vue Thymeleaf
    }
}