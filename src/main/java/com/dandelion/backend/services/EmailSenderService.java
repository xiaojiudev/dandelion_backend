package com.dandelion.backend.services;

public interface EmailSenderService {
    void sendEmail(String toEmail, String subject, String body);
    
    void sendRegistrationEmail(String email, String fullName);
}
