package project.back.dgi.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "actualite")
public class Actualite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "titre", nullable = false, columnDefinition = "TEXT")
    private String titre;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_ajout", nullable = false)
    private Timestamp dateAjout;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "actualite")
    private List<PieceJointe> piecesJointes;

    public Actualite() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getDateAjout() { return dateAjout; }
    public void setDateAjout(Timestamp dateAjout) { this.dateAjout = dateAjout; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<PieceJointe> getPiecesJointes() { return piecesJointes; }
    public void setPiecesJointes(List<PieceJointe> piecesJointes) { this.piecesJointes = piecesJointes; }

    // Méthode pour récupérer les pièces jointes qui sont des images (isImage = true)
    public List<PieceJointe> getPiecesJointesImages() {
        if (piecesJointes == null) {
            return List.of(); // Retourne une liste vide si piecesJointes est null
        }
    
        String uploads = "/uploads/"; // Chemin de base pour les fichiers uploadés
    
        return piecesJointes.stream()
                .filter(PieceJointe::isImage) // Filtre les pièces jointes où isImage est true
                .map(pieceJointe -> {
                    // Définir l'URL d'affichage pour chaque pièce jointe
                    pieceJointe.setDisplayUrl(uploads + pieceJointe.getNomFichier());
                    return pieceJointe;
                })
                .collect(Collectors.toList());
    }

    // Méthode pour récupérer les pièces jointes qui ne sont pas des images (isImage = false)
    public List<PieceJointe> getPiecesJointesNonImages() {
        if (piecesJointes == null) {
            return List.of(); // Retourne une liste vide si piecesJointes est null
        }
        return piecesJointes.stream()
                .filter(piece -> !piece.isImage()) // Filtre les pièces jointes où isImage est false
                .collect(Collectors.toList());
    }
}