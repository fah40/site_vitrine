package project.back.dgi.util;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HCaptchaUtil {
    public static boolean verifyHCaptcha(String hCaptchaResponse) {
        String secretKey = "ES_f980c08531fb40058afec57135824eba"; // Remplace avec ta clé secrète
        String apiUrl = "https://api.hcaptcha.com/siteverify";

        RestTemplate restTemplate = new RestTemplate();

        // Utiliser MultiValueMap pour encoder les données en application/x-www-form-urlencoded
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("secret", secretKey);
        requestBody.add("response", hCaptchaResponse);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, requestEntity, Map.class);

            // Vérification de la réponse
            Map responseBody = response.getBody();
            if (responseBody != null) {
                System.out.println("Réponse complète de hCaptcha : " + responseBody);

                Boolean success = (Boolean) responseBody.get("success");
                if (Boolean.FALSE.equals(success)) {
                    System.out.println("Échec de la validation hCaptcha. Erreurs : " + responseBody.get("error-codes"));
                }
                return Boolean.TRUE.equals(success);
            } else {
                System.out.println("Réponse vide de hCaptcha !");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la requête hCaptcha : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
