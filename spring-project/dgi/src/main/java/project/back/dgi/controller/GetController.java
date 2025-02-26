package project.back.dgi.controller;
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

@Controller
public class GetController {
    @Autowired
    private LangueService langueService;
    @Autowired
    private GeneralInfoValeurService generalInfoValeurService;
    @Autowired
    private GeneralInfoService generalInfoService;

    @GetMapping("/accueil")
    public String accueil(@RequestParam(required = false, defaultValue = "1") String langue, Model model) {
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

        @PostMapping("/update")
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
            // String newIconPath = "/uploads/" + iconeFile.getOriginalFilename();
            generalInfo.setIcone("newIconPath");
        }

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

                if ("titre".equals(field)) {
                    valeur.setTitre(params.get(key));
                } else if ("valeur".equals(field)) {
                    valeur.setValeur(params.get(key));
                }

                valeursToUpdate.add(valeur);
            }
        }

        generalInfoValeurService.saveAll(valeursToUpdate);

        return "redirect:/general_info_static/" + generalInfo.getCle();
    }

     
}
