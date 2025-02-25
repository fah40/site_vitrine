package project.back.dgi.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;
import java.security.SecureRandom;

public class PasswordUtil {

    @Value("${spring.datasource.driver-class-name}") // Pour détecter si PostgreSQL est utilisé
    private static String driverClassName;

    private static JdbcTemplate jdbcTemplate;

    public PasswordUtil(JdbcTemplate jdbcTemplate) {
        PasswordUtil.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Fonction pour crypter un mot de passe
     */
    public static String encrypt(String password) {
        if (isPostgreSQL()) {
            String sql = "SELECT crypt(?, gen_salt('bf'))";
            return jdbcTemplate.queryForObject(sql, String.class, password);
        } else {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        }
    }

    /**
     * Fonction pour comparer un mot de passe en clair avec un mot de passe crypté
     */
    public static boolean compareCrypt(String plainPassword, String encryptedPassword) {
        if (isPostgreSQL()) {
            String sql = "SELECT crypt(?, ?) = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Boolean.class, plainPassword, encryptedPassword, encryptedPassword))
                    .orElse(false);
        } else {
            return BCrypt.checkpw(plainPassword, encryptedPassword);
        }
    }

    /**
     * Détecte si PostgreSQL est utilisé comme base de données
     */
    private static boolean isPostgreSQL() {
        return driverClassName != null && driverClassName.contains("postgresql");
    }

    /**
     * Fonction pour la lattence en cas d'erreur login
     */
    public static void waitError (int millis) {
        try {
            Thread.sleep(millis); // Attente de 5 secondes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static void waitError () {
        waitError(5000);
    }

    /**
     * Genere un pin d'une longueur length
     * @param length
     * @return
     */
    public static String generatePin(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("La longueur du PIN doit être supérieure à 0.");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder pin = new StringBuilder();

        for (int i = 0; i < length; i++) {
            pin.append(random.nextInt(10)); // Génère un chiffre entre 0 et 9
        }

        return pin.toString();
    }
}
