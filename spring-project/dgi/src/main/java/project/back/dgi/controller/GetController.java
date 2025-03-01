package project.back.dgi.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.entity.Langue;
import project.back.dgi.service.GeneralInfoService;
import project.back.dgi.service.GeneralInfoValeurService;
import project.back.dgi.service.LangueService;
import project.back.dgi.util.MyUtil;
import project.back.dgi.util.PasswordUtil;

import java.nio.file.Path;

@Controller
public class GetController {
    @Autowired
    private LangueService langueService;
    @Autowired
    private GeneralInfoValeurService generalInfoValeurService;
    @Autowired
    private GeneralInfoService generalInfoService;

    @GetMapping("/accueil")
    public String accueil(@RequestParam(required = false, defaultValue = "2") String langue, Model model) {
        List<Langue> langues = langueService.findAll();
        long id_langue = Long.parseLong(langue);
        model.addAttribute("langues", langues);
        model.addAttribute("langueSelectionnee", id_langue);
        model.addAttribute("a_propos", generalInfoValeurService.getGeneralInfoByKey("a_propos", id_langue));
        model.addAttribute("dgi", generalInfoValeurService.getGeneralInfoByKey("dgi", id_langue));
        model.addAttribute("mot_du_dgi", generalInfoValeurService.getGeneralInfoByKey("mot_du_dgi", id_langue));
        model.addAttribute("legislation", generalInfoValeurService.getGeneralInfoByKey("legislation", id_langue));
        model.addAttribute("ressources", generalInfoValeurService.getGeneralInfoByKey("ressources", id_langue));
        model.addAttribute("analytiques_fiscales", generalInfoValeurService.getGeneralInfoByKey("analytiques_fiscales", id_langue));
        
        return "accueil";
    }

    @GetMapping("/general_info_static/{cle}")
    public String generalInfoStatic (@PathVariable String cle, Model model) {
        GeneralInfo generalInfo = generalInfoService.getByCle(cle).orElse(null);
        List<Langue> langues = langueService.findAll();
        Map<Long, GeneralInfoValeur> valeurs = generalInfoService.getGeneralInfoValeursByGeneralInfoId(generalInfo.getId());
        model.addAttribute("langues", langues);
        model.addAttribute("generalInfo", generalInfo);
        model.addAttribute("valeurs", valeurs);

        return "general_info_static";
    }

    @PostMapping("/general_info_static/update")
    public String updateGeneralInfo(@RequestParam Map<String, String> params,
                                    @RequestParam("iconeFile") MultipartFile iconeFile) {
        Long generalInfoId = Long.parseLong(params.get("id"));
        GeneralInfo generalInfo = generalInfoService.getById(generalInfoId).orElse(null);

        if (generalInfo == null) {
            return "redirect:/error";
        }

        // Mise à jour des champs simples
        generalInfo.setCle(params.get("cle"));
        generalInfo.setLien(params.get("lien"));

        // Gestion de l'icône
    if (!iconeFile.isEmpty()) {
        try {
            // Nom du fichier avec timestamp pour éviter les conflits
            String fileName = System.currentTimeMillis() + "_" + iconeFile.getOriginalFilename();
            String relativePath = "/uploads/img_uploads/" + fileName;
            
            // Emplacement du fichier dans /static/uploads/
            String uploadDir = "src/main/resources/static/uploads/img_uploads/";
            Path uploadPath = Paths.get(uploadDir);

            // Vérifier si le dossier existe, sinon le créer
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Copier le fichier
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(iconeFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Mettre à jour l'icône dans la BDD
            generalInfo.setIcone(relativePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        generalInfoService.save(generalInfo);

        // Mise à jour des valeurs de traduction
        Map<Long, GeneralInfoValeur> valeursExistantes = generalInfoService.getGeneralInfoValeursByGeneralInfoId(generalInfoId);
        List<GeneralInfoValeur> valeursToUpdate = new ArrayList<>();

        for (String key : params.keySet()) {
            System.out.println("key : "+key);
            if (key.contains(".")) {
                String[] parts = key.split("\\.");
                Long idLangue = Long.parseLong(parts[0]);
                String field = parts[1];

                GeneralInfoValeur valeur = valeursExistantes.getOrDefault(idLangue, new GeneralInfoValeur());
                valeur.setGeneralInfo(generalInfo);
                valeur.setLangue(langueService.findById(idLangue));

                if ("titre".equals(field)) {
                    valeur.setTitre(params.get(key));
                } else if ("valeur".equals(field)) {
                    valeur.setValeur(params.get(key));
                }

                valeursToUpdate.add(valeur);
            }
        }

        generalInfoValeurService.saveAll(valeursToUpdate);
        PasswordUtil.waitError(3000); // attendre que l'image soit correctement copie avant de rediriger
        return "redirect:/accueil";
    }

    @GetMapping("/voirfils")
    public String voirfilsGeneralInfo(@RequestParam("id") Long id, Model model) {
        GeneralInfo parent = generalInfoService.findById(id).orElse(null);
        List<GeneralInfo> children = generalInfoService.findChildrenByParent(parent);
        System.out.println("nombre " + children.size());
        model.addAttribute("parentGeneralInfo", parent);
        model.addAttribute("children", children);
        return "voirfils"; // La page JSP/Thymeleaf pour modifier l'info
    }

     
}
