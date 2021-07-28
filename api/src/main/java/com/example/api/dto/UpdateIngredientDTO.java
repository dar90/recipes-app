package com.example.api.dto;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UpdateIngredientDTO(
        @NotNull Long id,
        @NotBlank @Length(max = 255) String name,
        @NotBlank @Length(max = 100) String measureUnit,
        @NotNull Double amount
) {}
