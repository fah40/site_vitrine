package project.back.dgi.repository;

import project.back.dgi.entity.UserPin;
import project.back.dgi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPinRepository extends JpaRepository<UserPin, Long> {
    List<UserPin> findByUser(User user);
}
