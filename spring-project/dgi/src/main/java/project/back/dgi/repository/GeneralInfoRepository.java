package project.back.dgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.back.dgi.entity.GeneralInfo;
import java.util.Optional;

@Repository
public interface GeneralInfoRepository extends JpaRepository<GeneralInfo, Long> {
    Optional<GeneralInfo> findByCle(String cle);
}
