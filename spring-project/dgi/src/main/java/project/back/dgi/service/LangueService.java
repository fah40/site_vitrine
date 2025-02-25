package project.back.dgi.service;

import java.util.List;

import project.back.dgi.entity.Langue;

public interface LangueService {
    List<Langue> findAll();
    Langue findById(Long id);
    Langue save(Langue langue);
    void deleteById(Long id);
}