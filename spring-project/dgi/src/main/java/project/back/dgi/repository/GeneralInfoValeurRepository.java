package project.back.dgi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.entity.Langue;

@Repository
public interface GeneralInfoValeurRepository extends JpaRepository<GeneralInfoValeur, Long> {
    Optional<GeneralInfoValeur> findByGeneralInfoAndLangue(GeneralInfo generalInfo, Langue langue);
    List<GeneralInfoValeur> findByGeneralInfoId(Long generalInfo);
}
