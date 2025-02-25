package project.back.dgi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.back.dgi.entity.Configuration;
import project.back.dgi.repository.ConfigurationRepository;

import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public String getValueByKey(String key) {
        Optional<Configuration> config = configurationRepository.findByKeys(key);
        return config.map(Configuration::getValeurs).orElse(null);
    }
}
