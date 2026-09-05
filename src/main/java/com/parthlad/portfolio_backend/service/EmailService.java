package com.parthlad.portfolio_backend.service;

import com.parthlad.portfolio_backend.dto.ContactRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactRequest request) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("parthlad153@gmail.com");
        message.setSubject("New Portfolio Contact from " + request.getName());

        message.setText(
                "You received a new message from your portfolio website.\n\n" +
                "Name: " + request.getName() + "\n" +
                "Email: " + request.getEmail() + "\n\n" +
                "Message:\n" +
                request.getMessage()
        );

        mailSender.send(message);
    }
}