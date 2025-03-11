package project.back.dgi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import project.back.dgi.entity.Actualite;
import project.back.dgi.entity.PieceJointe;
import project.back.dgi.service.ActualiteService;
import project.back.dgi.service.PieceJointeService;

@Controller
public class ClientActualiteController {

    @Autowired
    private ActualiteService actualiteService;

    @Autowired
    private PieceJointeService pieceJointeService;

    @Value("${file.upload-dir}")
    private String storagePath;

    @GetMapping("/actualite/{idActu}")    
    public String getActuDetails (@PathVariable long idActu, Model model) {
        Actualite actualite = actualiteService.getActualiteById(idActu).orElse(null);
        List<PieceJointe> pieceJointes = pieceJointeService.getPiecesJointesNotImageByActualiteId(idActu);
        
        List<PieceJointe> images = pieceJointeService.getPiecesJointesImageByActualiteId(idActu);
        if (actualite != null) {
            model.addAttribute("actualite", actualite);
            model.addAttribute("pieceJointes", pieceJointes);
            for (PieceJointe pieceJointe : images) {
                String uploads = "/uploads/";
                pieceJointe.setDisplayUrl(uploads + pieceJointe.getNomFichier());
            }
            
            model.addAttribute("images", images);
            return "detail_actu";
        }

        return "redirect:/accueil";
    }
}