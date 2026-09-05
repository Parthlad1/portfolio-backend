package com.parthlad.portfolio_backend.controller;

import com.parthlad.portfolio_backend.dto.ContactRequest;
import com.parthlad.portfolio_backend.service.EmailService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> submitContact(@Valid @RequestBody ContactRequest request) {
        emailService.sendContactEmail(request);
        return ResponseEntity.ok("Message received successfully.");
    }
}