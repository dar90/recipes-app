package com.example.api.service;

import java.util.Optional;

import com.example.api.dto.RegistrationForm;
import com.example.api.model.User;
import com.example.api.model.UserRole;
import com.example.api.repository.UserRepository;

import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final EmailVerificationService emailVerificationService;

    public UserService(UserRepository repository, PasswordEncoder encoder, EmailVerificationService emailVerificationService) {
        this.repository = repository;
        this.encoder = encoder;
        this.emailVerificationService = emailVerificationService;
    }

    public Optional<User> createUser(RegistrationForm form) {
        User user = new User(null, form.login(), encoder.encode(form.password()), form.email(), false, false, UserRole.NEW, null, null);
        user = repository.save(user);
        try {
            emailVerificationService.createVerificationToken(user);
            emailVerificationService.sendMail(user);
            return Optional.of(user);
        } catch (MailException | NullPointerException e) {
            log.error(e.getMessage());
            repository.delete(user);
            return Optional.empty();
        }
    }

    public boolean loginAlreadyExists(String login) {
        return repository.findByLogin(login).isPresent();
    }

    public boolean emailAlreadyExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

}
