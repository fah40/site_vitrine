package project.back.dgi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.back.dgi.entity.GeneralInfo;

@Repository
public interface GeneralInfoRepository extends JpaRepository<GeneralInfo, Long> {
    Optional<GeneralInfo> findByCle(String cle);
    List<GeneralInfo> findByParentGeneralInfo(GeneralInfo parentGeneralInfo);
}
