package project.back.dgi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "total_visites")
public class TotalVisites {

    @Id
    private Long total; // Pas de clé primaire réelle, on utilise la valeur unique

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
