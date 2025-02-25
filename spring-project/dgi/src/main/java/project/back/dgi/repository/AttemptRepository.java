package project.back.dgi.repository;

import project.back.dgi.entity.Attempt;
import project.back.dgi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    Optional<Attempt> findByUser(User user);
}
