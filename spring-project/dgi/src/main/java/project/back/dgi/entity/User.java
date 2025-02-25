package project.back.dgi.entity;
import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_") 
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", length = 50) 
    private String name;

    @Column(name = "firstname", length = 50) // Correspondance avec "firstname" en base
    private String firstName;

    @Column(name = "validation_date") // Correspondance avec "validation_date"
    private LocalDateTime validationDate;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "id_admin", columnDefinition = "BOOLEAN DEFAULT false") // Correspondance avec "id_admin"
    private boolean isAdmin;

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDateTime getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDateTime validationDate) {
        this.validationDate = validationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", name=" + name + 
               ", firstName=" + firstName + ", validationDate=" + validationDate +
               ", isAdmin=" + isAdmin + "]";
    }
}
