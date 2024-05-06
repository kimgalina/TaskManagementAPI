package kg.kim.TaskManagementAPI.service.impl;

import kg.kim.TaskManagementAPI.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(ConfirmationToken confirmationToken, String confirmationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("opinion.techsupp@gmail.com");
        message.setTo(confirmationToken.getUser().getEmail());
        message.setSubject("Подтверждение аккаунта");
        message.setText("Для завершения регистрации перейдите по следующей ссылке: " + confirmationUrl);
        mailSender.send(message);

    }
}
