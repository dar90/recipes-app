package com.example.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.api.model.UserRole;

import org.hibernate.validator.constraints.Length;

public record UpdateUserDTO(
    @NotNull Long id,
    @NotBlank @Length(min = 5, max = 25) String login,
    @NotNull boolean blocked,
    @NotNull UserRole role
) {
    
}
