package com.example.api.controller;

import java.net.URI;

import javax.validation.Valid;

import com.example.api.dto.RegistrationForm;
import com.example.api.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationForm form, Errors validation) {
        if(validation.hasErrors() || !form.password().equals(form.repeatedPassword()))
            return ResponseEntity.badRequest().build();

        if(service.emailAlreadyExists(form.email()) || service.loginAlreadyExists(form.login()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return service.createUser(form)
                        .<ResponseEntity<Void>>map(user -> ResponseEntity.created(URI.create("http://localhost:8080/api/user/" + user.getId())).build())
                        .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    

}
