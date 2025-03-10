package project.back.dgi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.back.dgi.entity.PieceJointe;

@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {
    List<PieceJointe> findByActualiteId(Long actualiteId);
    List<PieceJointe> findByActualiteIdAndIsImageFalse(Long actualiteId);
    List<PieceJointe> findByActualiteIdAndIsImageTrue(Long actualiteId);
}