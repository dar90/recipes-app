package com.example.api.service;

import java.util.UUID;

import com.example.api.exception.InvalidEmailTokenException;
import com.example.api.model.EmailVerificationToken;
import com.example.api.model.User;
import com.example.api.model.UserRole;
import com.example.api.repository.EmailVerificationTokenRepoitory;
import com.example.api.repository.UserRepository;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {
    private final EmailVerificationTokenRepoitory repository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public EmailVerificationService(EmailVerificationTokenRepoitory repository, 
                                    UserRepository userRepository, 
                                    JavaMailSender mailSender) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public void verifyUserEmail(UUID token) throws InvalidEmailTokenException {
        EmailVerificationToken emailToken = repository.findByToken(token).orElseThrow(InvalidEmailTokenException::new);
        User user = emailToken.getUser();

        if(user.isEmailConfirmed())
            throw new InvalidEmailTokenException();

        user.setEmailConfirmed(true);
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    public EmailVerificationToken createVerificationToken(User user) {
        return repository.save(new EmailVerificationToken(null, user));
    }

    public void sendMail(User user) throws NullPointerException, MailException {
        EmailVerificationToken token = repository.findByUser(user).orElseThrow(NullPointerException::new);
        String confirmUrl = "https://recipes-app-2-api.herokuapp.com/api/verify/" + token.getToken();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@recipes-app.example.com");
        message.setTo(user.getEmail());
        message.setSubject("Verify Your email address " + user.getLogin());
        message.setText("Click this link to confirm Your email: " + confirmUrl);

        mailSender.send(message);
    }

}
