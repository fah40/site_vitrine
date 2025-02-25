package project.back.dgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.entity.Langue;
import project.back.dgi.entity.GeneralInfo;

import java.util.Optional;

@Repository
public interface GeneralInfoValeurRepository extends JpaRepository<GeneralInfoValeur, Long> {
    Optional<GeneralInfoValeur> findByGeneralInfoAndLangue(GeneralInfo generalInfo, Langue langue);
}
