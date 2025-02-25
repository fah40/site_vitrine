package project.back.dgi.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.back.dgi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
