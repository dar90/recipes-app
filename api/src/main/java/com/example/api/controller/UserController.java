package com.example.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.api.dto.LoginForm;
import com.example.api.dto.RegistrationForm;
import com.example.api.dto.UpdateUserDTO;
import com.example.api.dto.UserProfile;
import com.example.api.model.User;
import com.example.api.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        return service.generateAccessToken(loginForm)
                        .<ResponseEntity<String>>map(ResponseEntity::ok)
                        .orElse(ResponseEntity.badRequest().body(""));
    }

    @GetMapping("/profile")
    public UserProfile getUserProfile() {
        return service.userProfile();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.of(service.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(service.allUsers());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.of(service.patchUser(id, updates));
    }

    @PutMapping
    public ResponseEntity<User> putUser(@Valid @RequestBody UpdateUserDTO userDto) {
        return ResponseEntity.of(service.putUser(userDto));
    }

}
