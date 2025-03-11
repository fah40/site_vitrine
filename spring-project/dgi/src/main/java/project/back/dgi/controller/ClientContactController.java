package project.back.dgi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.back.dgi.service.GeneralInfoValeurService;


@Controller
public class ClientContactController {
    @Autowired
    private GeneralInfoValeurService generalInfoValeurService;

    @GetMapping("/contact")
    public String contact (@RequestParam(required = false, defaultValue="2") String langue, Model model) {
        long id_langue = Long.parseLong(langue);

        if (id_langue > 3) {
            id_langue = 2;
        }

        model.addAttribute("appeler", generalInfoValeurService.getGeneralInfoByKey("appeler", id_langue));
        model.addAttribute("ecrire", generalInfoValeurService.getGeneralInfoByKey("ecrire", id_langue));
        model.addAttribute("visiter", generalInfoValeurService.getGeneralInfoByKey("ecrire", id_langue));

        return "contact";
    }
    
}
