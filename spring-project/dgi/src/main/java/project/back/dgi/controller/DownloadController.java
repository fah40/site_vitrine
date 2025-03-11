package project.back.dgi.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.back.dgi.entity.PieceJointe;
import project.back.dgi.service.PieceJointeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class DownloadController {

    private final PieceJointeService pieceJointeService;

    public DownloadController(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam long id) throws FileNotFoundException {
        // Récupérer la pièce jointe par son ID
        PieceJointe pieceJointe = pieceJointeService.getPieceJointeById(id).orElse(null);

        // Vérifier si la pièce jointe existe
        if (pieceJointe == null) {
            throw new FileNotFoundException("Pièce jointe non trouvée avec l'ID : " + id);
        }

        // Récupérer le fichier à partir du chemin
        File file = new File(pieceJointe.getUrlFichier());

        // Vérifier si le fichier existe
        if (!file.exists()) {
            throw new FileNotFoundException("Fichier non trouvé : " + pieceJointe.getUrlFichier());
        }

        // Configurer les en-têtes de la réponse
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        // Renvoyer le fichier en tant que réponse
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }
}