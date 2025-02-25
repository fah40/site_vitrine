package project.back.dgi.service;

import org.springframework.stereotype.Service;

import project.back.dgi.entity.Attempt;
import project.back.dgi.entity.User;
import project.back.dgi.repository.UserRepository;
import project.back.dgi.repository.AttemptRepository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AttemptRepository attemptRepository;

    public UserService(UserRepository userRepository, AttemptRepository attemptRepository) {
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
    }

    // Recherche un utilisateur par email
    public Optional<User> findByEmail(String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    // Récupère ou crée une tentative d'authentification
    public Attempt getAttempt(User user) {
        Attempt attempt = attemptRepository.findByUser(user).orElse(null);
        if (attempt == null) {
            attempt = new Attempt(null, null, 0, user);
            attempt = attemptRepository.save(attempt);
        }
        return attempt;
    }
}
