package com.example.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegistrationForm(@NotBlank @Size(min = 4, max = 32) String login, 
                                @NotBlank @Email String email, 
                                @NotBlank @Size(min = 8, max = 50) String password, 
                                @NotBlank @Size(min = 8, max = 50) String repeatedPassword) {}
