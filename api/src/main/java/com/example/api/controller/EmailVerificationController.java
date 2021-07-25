package com.example.api.controller;

import java.util.UUID;

import com.example.api.exception.InvalidEmailTokenException;
import com.example.api.service.EmailVerificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/verify")
public class EmailVerificationController {
    
    private final EmailVerificationService service;

    public EmailVerificationController(EmailVerificationService service) {
        this.service = service;
    }

    @GetMapping("/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable UUID token) {
        try {
            service.verifyUserEmail(token);
            return ResponseEntity.ok("Your account is now active!");
        } catch (InvalidEmailTokenException e) {
            return ResponseEntity.badRequest().body("Oops! Something went wrong!");
        }
    }
    

}
