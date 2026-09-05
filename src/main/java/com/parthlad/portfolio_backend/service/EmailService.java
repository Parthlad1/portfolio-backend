package com.parthlad.portfolio_backend.service;

import com.parthlad.portfolio_backend.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromEmail;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username}") String fromEmail) {

        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    public void sendContactEmail(ContactRequest request) {

        System.out.println("=== CONTACT EMAIL START ===");
        System.out.println("Sending email from: " + fromEmail);
        System.out.println("Visitor email: " + request.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(fromEmail);
        message.setReplyTo(request.getEmail());
        message.setSubject("New Portfolio Contact — " + request.getName());

        message.setText(
                "You received a new message from your portfolio website.\n\n" +
                "Name: " + request.getName() + "\n" +
                "Email: " + request.getEmail() + "\n\n" +
                "Message:\n" +
                request.getMessage() + "\n\n" +
                "You can reply directly to this email to respond to " +
                request.getName() + "."
        );

        System.out.println("Calling Gmail SMTP...");

        mailSender.send(message);

        System.out.println("=== CONTACT EMAIL SENT SUCCESSFULLY ===");
    }
}