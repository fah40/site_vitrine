package project.back.dgi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.back.dgi.entity.Langue;
import project.back.dgi.service.LangueService;

@Controller
public class GetController {
    @Autowired
    private LangueService langueService;

    @GetMapping("/accueil")
    public String accueil(@RequestParam(required = false, defaultValue = "FRA") String langue, Model model) {
        List<Langue> langues = langueService.findAll();
        model.addAttribute("langues", langues);
        model.addAttribute("langueSelectionnee", langue);
        return "accueil";
    }
     
}
