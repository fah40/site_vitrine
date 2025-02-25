package project.back.dgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.back.dgi.entity.Configuration;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    
    // Recherche par clé unique
    Optional<Configuration> findByKeys(String keys);
}
