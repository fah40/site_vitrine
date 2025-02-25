package project.back.dgi.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "configuration", uniqueConstraints = @UniqueConstraint(columnNames = "keys"))
public class Configuration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keys", nullable = false, unique = true, length = 50)
    private String keys;

    @Column(name = "valeurs", columnDefinition = "TEXT")
    private String valeurs;

    // Constructeurs
    public Configuration() {}

    public Configuration(String keys, String valeurs) {
        this.keys = keys;
        this.valeurs = valeurs;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getValeurs() {
        return valeurs;
    }

    public void setValeurs(String valeurs) {
        this.valeurs = valeurs;
    }
}
