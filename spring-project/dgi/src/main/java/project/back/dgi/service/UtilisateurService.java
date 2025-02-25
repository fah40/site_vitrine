package project.back.dgi.service;

import java.util.List;

import project.back.dgi.entity.Utilisateur;

public interface UtilisateurService {
    List<Utilisateur> findAll();
    Utilisateur findById(Long id);
    Utilisateur save(Utilisateur utilisateur);
    void deleteById(Long id);
}