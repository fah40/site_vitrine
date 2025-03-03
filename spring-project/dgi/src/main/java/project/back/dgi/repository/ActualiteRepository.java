package project.back.dgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.back.dgi.entity.Actualite;

@Repository
public interface ActualiteRepository extends JpaRepository<Actualite, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}