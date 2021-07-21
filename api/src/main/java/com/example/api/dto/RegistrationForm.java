package com.example.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegistrationForm(@NotBlank @Size(min = 5, max = 25) String login, 
                                @NotBlank @Email String email, 
                                @NotBlank @Size(min = 5, max = 55) String password, 
                                @NotBlank @Size(min = 5, max = 55) String repeatedPassword) {}
