package com.example.api.controller;

import java.net.URI;

import com.example.api.dto.RegistrationForm;
import com.example.api.model.User;
import com.example.api.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> postMethodName(@RequestBody RegistrationForm form) {
        if(!form.password().equals(form.repeatedPassword()))
            return ResponseEntity.badRequest().build();

        if(service.emailAlreadyExists(form.email()) || service.loginAlreadyExists(form.login()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        User user = service.createUser(form);
        return ResponseEntity.created(URI.create("http://localhost:8080/api/user/" + user.getId())).build();
    }
    

}
