package project.back.dgi.service;

import org.springframework.stereotype.Service;

import project.back.dgi.entity.Visite;
import project.back.dgi.repository.VisiteRepository;

@Service
public class VisiteService {

    private final VisiteRepository visiteRepository;

    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    public Visite insertVisite(Visite visite) {
        return visiteRepository.save(visite);
    }
}
