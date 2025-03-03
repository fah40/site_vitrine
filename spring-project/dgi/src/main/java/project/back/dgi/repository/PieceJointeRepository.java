package project.back.dgi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.back.dgi.entity.PieceJointe;

@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {
    // Méthodes personnalisées (si nécessaire)
    // Exemple : Rechercher des pièces jointes par actualité
    List<PieceJointe> findByActualiteId(Long actualiteId);
}