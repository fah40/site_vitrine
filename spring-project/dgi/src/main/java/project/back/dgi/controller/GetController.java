package project.back.dgi.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.entity.Langue;
import project.back.dgi.service.ActualiteService;
import project.back.dgi.service.GeneralInfoService;
import project.back.dgi.service.GeneralInfoValeurService;
import project.back.dgi.service.LangueService;
import project.back.dgi.service.TotalVisitesService;
import project.back.dgi.util.PasswordUtil;

@Controller
@RequestMapping("/admin")
public class GetController {
    @Autowired
    private LangueService langueService;
    @Autowired
    private GeneralInfoValeurService generalInfoValeurService;
    @Autowired
    private GeneralInfoService generalInfoService;
    @Autowired
    private ActualiteService actualiteService;
    @Autowired
    private TotalVisitesService totalVisitesService;
    @Value("${file.popup-upload-dir}")
    private String popupUploadPath;


    @GetMapping("/go_back_home")
    public String deconnection (HttpSession session) {
        session.invalidate();
        return "redirect:/accueil";
    }

    @GetMapping("/accueil")
    public String accueil(@RequestParam(required = false, defaultValue = "2") String langue, Model model) {
        List<Langue> langues = langueService.findAll();
        long id_langue = 2;

        try {
            id_langue = Long.parseLong(langue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id_langue > 3 || id_langue < 1) {
            id_langue = 2;
        }

        model.addAttribute("langues", langues);
        model.addAttribute("langueSelectionnee", id_langue);
        model.addAttribute("a_propos", generalInfoValeurService.getGeneralInfoByKey("a_propos", id_langue));
        model.addAttribute("dgi", generalInfoValeurService.getGeneralInfoByKey("dgi", id_langue));
        model.addAttribute("mot_du_dgi", generalInfoValeurService.getGeneralInfoByKey("mot_du_dgi", id_langue));
        model.addAttribute("legislation", generalInfoValeurService.getGeneralInfoByKey("/legislation", id_langue));
        model.addAttribute("ressources", generalInfoValeurService.getGeneralInfoByKey("/ressources", id_langue));
        model.addAttribute("analytiques_fiscales", generalInfoValeurService.getGeneralInfoByKey("/analytiques_fiscales", id_langue));
        model.addAttribute("historique", generalInfoValeurService.getGeneralInfoByKey("/historique", id_langue));
        model.addAttribute("vision", generalInfoValeurService.getGeneralInfoByKey("/vision", id_langue));
        model.addAttribute("actualites", generalInfoValeurService.getGeneralInfoByKey("actualites", id_langue));
        model.addAttribute("partenaires", generalInfoValeurService.getGeneralInfoByKey("partenaire", id_langue));
        model.addAttribute("bureau", generalInfoValeurService.getGeneralInfoByKey("bureau", id_langue));
        model.addAttribute("contribuable", generalInfoValeurService.getGeneralInfoByKey("contribuable", id_langue));
        model.addAttribute("recette", generalInfoValeurService.getGeneralInfoByKey("recette", id_langue));
        model.addAttribute("attributions", generalInfoValeurService.getGeneralInfoByKey("/attributions", id_langue));
        model.addAttribute("e_service", generalInfoValeurService.getGeneralInfoByKey("/e_service", id_langue));
        model.addAttribute("votre_avis", generalInfoValeurService.getGeneralInfoByKey("/votre_avis", id_langue));
        model.addAttribute("centre_contact", generalInfoValeurService.getGeneralInfoByKey("centre_contact", id_langue));

        model.addAttribute("allActualites", actualiteService.getAllActualites());

        List<GeneralInfo> list = generalInfoService.findChildrenByParent(generalInfoService.getByCle("navigation").orElse(null));
        List<GeneralInfoValeur> values = new ArrayList<>();

        System.out.println("taille : " + list.size());

        for (GeneralInfo item : list) {
            values.add(generalInfoValeurService.getGeneralInfoByKey(item.getCle(), id_langue));
        }

        model.addAttribute("allNavs", values);

        model.addAttribute("totalVisites", totalVisitesService.getTotalVisites());

        // Vérifier si le dossier d'upload existe, sinon le créer
        File uploadDir = new File(popupUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String popUpImage = "";

        File[] existingFiles = uploadDir.listFiles();
        if (existingFiles != null) {
            for (File existingFile : existingFiles) {
                popUpImage = existingFile.getPath();
            }
        }

        if (!popUpImage.isEmpty()) {
            if (popUpImage.contains("\\uploads")) {
                popUpImage = popUpImage.substring(popUpImage.indexOf("\\uploads"));
            }
        }

        model.addAttribute("popUpImage", popUpImage);

        return "index";
    }

    @GetMapping("/general_info_static")
    public String generalInfoStatic (@RequestParam String cle, Model model) {
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
                                @RequestParam("icone") String icone) {
        Long generalInfoId = Long.parseLong(params.get("id"));
        GeneralInfo generalInfo = generalInfoService.getById(generalInfoId).orElse(null);

        if (generalInfo == null) {
            return "redirect:/error";
        }

        // Mise à jour des champs simples
        generalInfo.setCle(params.get("cle"));
        generalInfo.setLien(params.get("lien"));

        // Gestion de l'icône
        generalInfo.setIcone(icone);

        generalInfoService.save(generalInfo);

        // Mise à jour des valeurs de traduction
        Map<Long, GeneralInfoValeur> valeursExistantes = generalInfoService.getGeneralInfoValeursByGeneralInfoId(generalInfoId);
        List<GeneralInfoValeur> valeursToUpdate = new ArrayList<>();

        for (String key : params.keySet()) {
            if (key.contains(".")) {
                String[] parts = key.split("\\.");
                Long idLangue = Long.parseLong(parts[0]);
                String field = parts[1];

                GeneralInfoValeur valeur = valeursExistantes.getOrDefault(idLangue, new GeneralInfoValeur());
                valeur.setGeneralInfo(generalInfo);
                valeur.setLangue(langueService.findById(idLangue));

                switch (field) {
                    case "titre":

                    valeur.setTitre(params.getOrDefault(key,""));

                        break;
                    case "entete":
                        valeur.setEntete(params.getOrDefault(key,""));
                        break;
                    case "bouton":
                        valeur.setBouton(params.getOrDefault(key,""));
                        break;
                    case "valeur":
                        valeur.setValeur(params.getOrDefault(key,""));
                        break;
                }

                valeursToUpdate.add(valeur);
            }
        }

        generalInfoValeurService.saveAll(valeursToUpdate);
        PasswordUtil.waitError(3000);
        return "redirect:/admin/accueil";
    }
}
