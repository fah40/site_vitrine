package project.back.dgi.controller;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.entity.Langue;
import project.back.dgi.entity.Visite;
import project.back.dgi.service.ActualiteService;
import project.back.dgi.service.GeneralInfoService;
import project.back.dgi.service.GeneralInfoValeurService;
import project.back.dgi.service.LangueService;
import project.back.dgi.service.TotalVisitesService;
import project.back.dgi.service.VisiteService;

@Controller
public class ClientGetController {

    private final ActualiteController actualiteController;
    @Autowired
    private LangueService langueService;
    @Autowired
    private GeneralInfoValeurService generalInfoValeurService;
    @Autowired
    private GeneralInfoService generalInfoService;
    @Autowired
    private ActualiteService actualiteService;
    @Autowired
    private VisiteService visiteService;
    @Autowired
    private TotalVisitesService totalVisitesService;
    @Value("${file.popup-upload-dir}")
    private String popupUploadPath;

    ClientGetController(ActualiteController actualiteController) {
        this.actualiteController = actualiteController;
    }

    @GetMapping("/accueil")
    public String accueil(@RequestParam(required = false, defaultValue = "2") String langue, Model model, HttpSession session) {
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

        // comptage du nombre de visite 
        if (session.getAttribute("lastVisitDate") == null) {
            LocalDate now = LocalDate.now();
            session.setAttribute("lastVisitDate", now);
            Visite visite = new Visite(now);
            visiteService.insertVisite(visite);
            
        } else {
            // Vérification de la date de la dernière visite
            LocalDate lastVisitDate = (LocalDate) session.getAttribute("lastVisitDate");
            if (lastVisitDate != null && lastVisitDate.isBefore(LocalDate.now().minusDays(1))) {
                LocalDate now = LocalDate.now();
                session.setAttribute("lastVisitDate", now);
                Visite visite = new Visite(now);
                visiteService.insertVisite(visite);
            }
        }

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

        model.addAttribute("totalVisites", totalVisitesService.getTotalVisites());

        return "index-client";
    }

    @GetMapping({"/",""})
    public String index() {
        return "redirect:/accueil";
    }


}
