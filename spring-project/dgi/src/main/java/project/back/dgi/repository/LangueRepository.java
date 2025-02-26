package project.back.dgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.back.dgi.entity.Langue;

@Repository
public interface LangueRepository extends JpaRepository<Langue, Long> {
    
}
