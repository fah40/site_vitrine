package project.back.dgi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.back.dgi.entity.GeneralInfoValeur;
import project.back.dgi.entity.Langue;
import project.back.dgi.entity.GeneralInfo;
import project.back.dgi.repository.GeneralInfoValeurRepository;
import project.back.dgi.repository.LangueRepository;
import project.back.dgi.repository.GeneralInfoRepository;

import java.util.Optional;

@Service
public class GeneralInfoValeurService {

    @Autowired
    private GeneralInfoValeurRepository generalInfoValeurRepository;

    @Autowired
    private GeneralInfoRepository generalInfoRepository;

    @Autowired
    private LangueRepository langueRepository;

    public GeneralInfoValeur getGeneralInfoByKey(String cle, Long idLangue) {
        Optional<GeneralInfo> generalInfoOpt = generalInfoRepository.findByCle(cle);
        if (generalInfoOpt.isEmpty()) {
            throw new RuntimeException("Aucune information générale trouvée pour la clé : " + cle);
        }

        Optional<Langue> langueOpt = langueRepository.findById(idLangue);
        if (langueOpt.isEmpty()) {
            throw new RuntimeException("Langue non trouvée pour le code : " + idLangue);
        }

        Optional<GeneralInfoValeur> generalInfoValeurOpt = 
            generalInfoValeurRepository.findByGeneralInfoAndLangue(generalInfoOpt.get(), langueOpt.get());

        return generalInfoValeurOpt.orElseThrow(() -> 
            new RuntimeException("Aucune valeur trouvée pour la clé : " + cle + " et la langue : " + idLangue));
    }
}
