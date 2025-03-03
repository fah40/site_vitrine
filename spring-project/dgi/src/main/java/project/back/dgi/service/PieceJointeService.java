package project.back.dgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.back.dgi.entity.PieceJointe;
import project.back.dgi.repository.PieceJointeRepository;

@Service
public class PieceJointeService {

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    // Récupérer toutes les pièces jointes
    public List<PieceJointe> getAllPiecesJointes() {
        return pieceJointeRepository.findAll();
    }

    // Récupérer une pièce jointe par son ID
    public Optional<PieceJointe> getPieceJointeById(Long id) {
        return pieceJointeRepository.findById(id);
    }

    // Enregistrer une nouvelle pièce jointe
    public PieceJointe savePieceJointe(PieceJointe pieceJointe) {
        return pieceJointeRepository.save(pieceJointe);
    }

    // Supprimer une pièce jointe par son ID
    public void deletePieceJointe(Long id) {
        pieceJointeRepository.deleteById(id);
    }

    // Mettre à jour une pièce jointe
    public PieceJointe updatePieceJointe(Long id, PieceJointe pieceJointeDetails) {
        PieceJointe pieceJointe = pieceJointeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pièce jointe non trouvée pour cet ID :: " + id));

        pieceJointe.setUrlFichier(pieceJointeDetails.getUrlFichier());
        pieceJointe.setActualite(pieceJointeDetails.getActualite());

        return pieceJointeRepository.save(pieceJointe);
    }

    // Récupérer les pièces jointes d'une actualité spécifique
    public List<PieceJointe> getPiecesJointesByActualiteId(Long actualiteId) {
        return pieceJointeRepository.findByActualiteId(actualiteId);
    }
}