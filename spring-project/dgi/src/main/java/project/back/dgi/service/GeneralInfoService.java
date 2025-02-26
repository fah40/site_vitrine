package project.back.dgi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.repository.GeneralInfoRepository;
import project.back.dgi.repository.GeneralInfoValeurRepository;

@Service
public class GeneralInfoService {
    @Autowired
    private GeneralInfoRepository generalInfoRepository;
    @Autowired
    private GeneralInfoValeurRepository generalInfoValeurRepository;


    public Optional<GeneralInfo> getByCle(String cle) {
        return generalInfoRepository.findByCle(cle);
    }

    public Map<Long, GeneralInfoValeur> getGeneralInfoValeursByGeneralInfoId(Long generalInfoId) {
        // Récupérer toutes les GeneralInfoValeur associées à l'id_general_info
        List<GeneralInfoValeur> valeurs = generalInfoValeurRepository.findByGeneralInfoId(generalInfoId);

        // Construire le HashMap
        Map<Long, GeneralInfoValeur> map = new HashMap<>();
        for (GeneralInfoValeur valeur : valeurs) {
            map.put(valeur.getLangue().getId(), valeur);
        }

        return map;
    }

    public Optional<GeneralInfo> getById(Long id) {
        return generalInfoRepository.findById(id);
    }

    public List<GeneralInfo> findAll() {
        return generalInfoRepository.findAll();
    }

    public GeneralInfo save(GeneralInfo generalInfo) {
        return generalInfoRepository.save(generalInfo);
    }

 

    public void saveGeneralInfoValeurs(Map<Long, GeneralInfoValeur> valeurs) {
        generalInfoValeurRepository.saveAll(valeurs.values());
    }
}
