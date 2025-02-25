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
@Table(name = "general_info_valeur")
public class GeneralInfoValeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "valeur", nullable = false)
    private String valeur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_langue", nullable = false)
    private Langue langue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_general_info", nullable = false)
    private GeneralInfo generalInfo;

    public GeneralInfoValeur() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getValeur() { return valeur; }
    public void setValeur(String valeur) { this.valeur = valeur; }
    public Langue getLangue() { return langue; }
    public void setLangue(Langue langue) { this.langue = langue; }
    public GeneralInfo getGeneralInfo() { return generalInfo; }
    public void setGeneralInfo(GeneralInfo generalInfo) { this.generalInfo = generalInfo; }
}