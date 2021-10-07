package com.example.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UpdateUserEmailDTO(
    @NotBlank String password,
    @NotBlank @Email String newEmail
) {
    
}
