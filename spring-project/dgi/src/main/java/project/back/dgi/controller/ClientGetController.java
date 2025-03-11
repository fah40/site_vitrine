package project.back.dgi.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.entity.Langue;
import project.back.dgi.service.ActualiteService;
import project.back.dgi.service.GeneralInfoService;
import project.back.dgi.service.GeneralInfoValeurService;
import project.back.dgi.service.LangueService;

@Controller
public class ClientGetController {
    @Autowired
    private LangueService langueService;
    @Autowired
    private GeneralInfoValeurService generalInfoValeurService;
    @Autowired
    private GeneralInfoService generalInfoService;
    @Autowired
    private ActualiteService actualiteService;

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

        return "index-client";
    }

    @GetMapping({"/",""})
    public String index() {
        return "redirect:/accueil";
    }


}
