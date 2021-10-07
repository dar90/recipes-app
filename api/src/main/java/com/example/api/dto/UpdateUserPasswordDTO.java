package com.example.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UpdateUserPasswordDTO(
    @NotBlank String oldPassword,
    @NotBlank @Size(min = 5, max = 55) String newPassword,
    @NotBlank @Size(min = 5, max = 55) String repeatedNewPassword
) {
    
}
