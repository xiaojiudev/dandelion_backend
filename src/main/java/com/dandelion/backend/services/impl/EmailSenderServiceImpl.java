package com.dandelion.backend.services.impl;

import com.dandelion.backend.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Dandelion ecommerce <zzsakura2020@gmail.com>");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail sent successfully!");
    }

    @Override
    @Async
    public void sendRegistrationEmail(String email, String fullName) {
        this.sendEmail(email, "Welcome to Our Service", "Dear [" + fullName + "],\n\n");
    }
}
