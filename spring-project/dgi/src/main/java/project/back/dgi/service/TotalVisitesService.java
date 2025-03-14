package project.back.dgi.service;

import org.springframework.stereotype.Service;

import project.back.dgi.entity.TotalVisites;
import project.back.dgi.repository.TotalVisitesRepository;

@Service
public class TotalVisitesService {

    private final TotalVisitesRepository totalVisitesRepository;

    public TotalVisitesService(TotalVisitesRepository totalVisitesRepository) {
        this.totalVisitesRepository = totalVisitesRepository;
    }

    public Long getTotalVisites() {
        TotalVisites total = totalVisitesRepository.getTotalVisites();
        return total != null ? total.getTotal() : 0;
    }
}
