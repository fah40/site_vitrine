package project.back.dgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.back.dgi.entity.Visite;

public interface VisiteRepository extends JpaRepository<Visite, Long> {
}
