package project.back.dgi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attempts")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_next_attempt")
    private LocalDateTime dateNextAttempt;

    @Column(name = "count_attempt", nullable = false)
    private int countAttempt;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Attempt (){}

    public Attempt(Long id, LocalDateTime dateNextAttempt, int countAttempt, User user) {
        this.id = id;
        this.dateNextAttempt = dateNextAttempt;
        this.countAttempt = countAttempt;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateNextAttempt() {
        return dateNextAttempt;
    }

    public void setDateNextAttempt(LocalDateTime dateNextAttempt) {
        this.dateNextAttempt = dateNextAttempt;
    }

    public int getCountAttempt() {
        return countAttempt;
    }

    public void setCountAttempt(int countAttempt) {
        this.countAttempt = countAttempt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
