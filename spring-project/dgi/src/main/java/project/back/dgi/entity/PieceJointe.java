package project.back.dgi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "piece_jointe")
public class PieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "url_fichier", nullable = false)
    private String urlFichier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_actualite", nullable = false)
    private Actualite actualite;

    public PieceJointe() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUrlFichier() { return urlFichier; }
    public void setUrlFichier(String urlFichier) { this.urlFichier = urlFichier; }
    public Actualite getActualite() { return actualite; }
    public void setActualite(Actualite actualite) { this.actualite = actualite; }
}