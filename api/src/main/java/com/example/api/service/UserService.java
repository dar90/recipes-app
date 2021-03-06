package com.example.api.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.api.dto.LoginForm;
import com.example.api.dto.RegistrationForm;
import com.example.api.dto.UpdateUserDTO;
import com.example.api.dto.UserProfile;
import com.example.api.model.User;
import com.example.api.model.UserRole;
import com.example.api.repository.EmailVerificationTokenRepoitory;
import com.example.api.repository.UserRepository;

import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final EmailVerificationService emailVerificationService;
    private final Algorithm jwtAlgorithm;
    private final AuthenticationManager authManager;
    private final EmailVerificationTokenRepoitory emailVerificationTokenRepoitory;

    public UserService(UserRepository repository, 
                        PasswordEncoder encoder, 
                        EmailVerificationService emailVerificationService, 
                        Algorithm jwtAlgorithm,
                        AuthenticationManager authManager,
                        EmailVerificationTokenRepoitory emailVerificationTokenRepoitory) {
        this.repository = repository;
        this.encoder = encoder;
        this.emailVerificationService = emailVerificationService;
        this.jwtAlgorithm = jwtAlgorithm;
        this.authManager = authManager;
        this.emailVerificationTokenRepoitory = emailVerificationTokenRepoitory;
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
            emailVerificationTokenRepoitory.findByUser(user).ifPresent(emailVerificationTokenRepoitory::delete);
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

    public Optional<String> generateAccessToken(LoginForm loginForm) {
        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginForm.login(), loginForm.password()
                )
            );

            User user = (User) auth.getPrincipal();

            String token = JWT.create()
                                    .withExpiresAt(
                                        Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toInstant()))
                                    .withIssuedAt(
                                        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                                    .withClaim("name", user.getLogin())
                                    .withClaim("role", user.getRole().toString())
                                    .sign(jwtAlgorithm);

            return Optional.of(token);

        } catch (Exception e) {
            log.error("Login failed: " + loginForm.login());
            return Optional.empty();
        }
    }

    public UserProfile userProfile() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = repository.getById(currentUser.getId());
        return new UserProfile(currentUser.getLogin(),
                                            currentUser.getEmail(),
                                            currentUser.getRecipes(),
                                            currentUser.getOpinions());
    }

    public Optional<User> getUser(Long id) {
        return repository.findById(id).or(Optional::empty);
    }

    public List<User> allUsers() {
        return repository.findAll();
    }

    public Optional<User> patchUser(Long id, Map<String, Object> updates) {
        Optional<User> optionalUser = repository.findById(id);

        if(optionalUser.isEmpty())
            return Optional.empty();

        User user = optionalUser.get();
        
        if(updates.containsKey("blocked")) 
            if(updates.get("blocked") instanceof Boolean blocked)
                user.setBlocked(blocked);

        if(updates.containsKey("role"))
            if(updates.get("role") instanceof String roleName) {
                try {
                    user.setRole(UserRole.valueOf(roleName));
                } catch (IllegalArgumentException e) {
                    log.error("Wrong role name");
                }
            }
                
        if(updates.containsKey("login"))
            if(updates.get("login") instanceof String login && login.length() >= 5 && login.length() <= 25)
                user.setLogin(login);

        if(updates.containsKey("emailConfirmed"))
            if(updates.get("emailConfirmed") instanceof Boolean emailConfirmed)
                user.setEmailConfirmed(emailConfirmed);
        
        user = repository.save(user);
        return Optional.of(user);
    }

    public Optional<User> putUser(UpdateUserDTO userDTO) {
        Optional<User> optionalUser = repository.findById(userDTO.id());

        if(optionalUser.isEmpty())
            return Optional.empty();

        User user = optionalUser.get();
        user.setBlocked(userDTO.blocked());
        user.setRole(userDTO.role());
        user.setLogin(userDTO.login());
        user = repository.save(user);

        return Optional.of(user);
    }

    public boolean isPasswordValid(String password) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return encoder.matches(password, currentUser.getPassword());
    }

    public void updateCurrentUserPassword(String newPassword) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser.setPassword(encoder.encode(newPassword));
        repository.save(currentUser);
    }

    public void updateCurrentUserEmail(String newEmail) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser.setEmail(newEmail);
        repository.save(currentUser);
    }

}
