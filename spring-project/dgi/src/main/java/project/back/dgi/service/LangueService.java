package project.back.dgi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.back.dgi.entity.Langue;
import project.back.dgi.repository.LangueRepository;
@Service
public class LangueService {
    @Autowired
    private LangueRepository langueRepository;

    public List<Langue> findAll() {
        return langueRepository.findAll();
    }

    public Langue findById(Long id) {
        return langueRepository.findById(id).orElse(null);
    }

    public Langue save(Langue langue) {
        return langueRepository.save(langue);
    }

    public void deleteById(Long id) {
        langueRepository.deleteById(id);
    }
}