package com.example.api.service;

import com.example.api.dto.RegistrationForm;
import com.example.api.model.User;
import com.example.api.model.UserRole;
import com.example.api.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User createUser(RegistrationForm form) {
        User user = new User(null, form.login(), encoder.encode(form.password()), form.email(), false, false, UserRole.NEW, null, null);
        user = repository.save(user);
        return user;
    }

    public boolean loginAlreadyExists(String login) {
        return repository.findByLogin(login).isPresent();
    }

    public boolean emailAlreadyExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

}
