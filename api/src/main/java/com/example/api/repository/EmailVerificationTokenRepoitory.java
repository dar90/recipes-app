package com.example.api.repository;

import java.util.Optional;
import java.util.UUID;

import com.example.api.model.EmailVerificationToken;
import com.example.api.model.User;

import org.springframework.data.repository.Repository;

public interface EmailVerificationTokenRepoitory extends Repository<EmailVerificationToken, UUID> {
    Optional<EmailVerificationToken> findByToken(UUID token);
    EmailVerificationToken save(EmailVerificationToken token);
    Optional<EmailVerificationToken> findByUser(User user);
}
