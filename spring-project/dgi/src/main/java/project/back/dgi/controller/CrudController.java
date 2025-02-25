package project.back.dgi.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
// @RequestMapping("/notprotected")
public class CrudController {

    @GetMapping("/test")
    public String test (){
        return "test";
    }
    @PostMapping("/sauvegarder")
    public String sauvegarder(@RequestParam("contenu") String contenu, Model model) {
        // Ici tu peux sauvegarder 'contenu' dans ta base de données
        System.out.println("Contenu HTML enregistré : " + contenu);
        model.addAttribute("message", "Texte enregistré avec succès !");
        return "test"; // Une page de confirmation
    }
}
