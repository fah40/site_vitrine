package project.back.dgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.back.dgi.entity.Actualite;
import project.back.dgi.repository.ActualiteRepository;

@Service
public class ActualiteService {

    @Autowired
    private ActualiteRepository actualiteRepository;

    public List<Actualite> getAllActualites() {
        return actualiteRepository.findAll();
    }

    public Optional<Actualite> getActualiteById(Long id) {
        return actualiteRepository.findById(id);
    }

    public Actualite saveActualite(Actualite actualite) {
        return actualiteRepository.save(actualite);
    }

    public void deleteActualite(Long id) {
        actualiteRepository.deleteById(id);
    }

    public Actualite updateActualite(Long id, Actualite actualiteDetails) {
        Actualite actualite = actualiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actualite not found for this id :: " + id));

        actualite.setTitre(actualiteDetails.getTitre());
        actualite.setDescription(actualiteDetails.getDescription());
        actualite.setDateAjout(actualiteDetails.getDateAjout());
        actualite.setUser(actualiteDetails.getUser());

        return actualiteRepository.save(actualite);
    }
}