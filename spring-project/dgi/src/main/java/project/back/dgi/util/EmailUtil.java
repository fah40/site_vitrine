package project.back.dgi.util; 

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
    
    @Value("${spring.mail.username}")
    private String emailSender; // Récupération depuis application.properties

    private static final String DISPLAY_NAME = "CommunITDev";

    private final JavaMailSender emailSenderService;

    public EmailUtil(JavaMailSender emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    /**
     * Envoie un email au format HTML à l'adresse spécifiée.
     *
     * @param recipient L'adresse email du destinataire
     * @param subject   Le sujet du message
     * @param content   Le contenu principal du message au format HTML
     */
    public void sendHtmlEmail(String recipient, String subject, String content) {
        try {
            MimeMessage message = emailSenderService.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(new InternetAddress(emailSender, DISPLAY_NAME)); // Utilisation de la valeur injectée
            helper.setTo(recipient);
            helper.setSubject(subject);

            String bodyHtml = "<html><body><h1>Bienvenue sur notre service</h1><p>" + content + "</p></body></html>";
            helper.setText(bodyHtml, true);

            emailSenderService.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
