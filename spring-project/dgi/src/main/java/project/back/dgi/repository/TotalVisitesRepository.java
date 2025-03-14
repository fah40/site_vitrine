package project.back.dgi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import project.back.dgi.entity.TotalVisites;

public interface TotalVisitesRepository extends CrudRepository<TotalVisites, Long> {
    
    @Query("SELECT t FROM TotalVisites t")
    TotalVisites getTotalVisites();
}
