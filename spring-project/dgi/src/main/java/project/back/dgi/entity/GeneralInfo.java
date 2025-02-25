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
@Table(name = "general_info")
public class GeneralInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "cle", nullable = false)
    private String cle;

    @Column(name = "icone")
    private String icone;

    @Column(name = "lien")
    private String lien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_groupe")
    private Groupe groupe;

    public GeneralInfo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCle() { return cle; }
    public void setCle(String cle) { this.cle = cle; }
    public String getIcone() { return icone; }
    public void setIcone(String icone) { this.icone = icone; }
    public String getLien() { return lien; }
    public void setLien(String lien) { this.lien = lien; }
    public Groupe getGroupe() { return groupe; }
    public void setGroupe(Groupe groupe) { this.groupe = groupe; }
}