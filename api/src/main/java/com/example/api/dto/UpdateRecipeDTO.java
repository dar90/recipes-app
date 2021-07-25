package com.example.api.dto;

import com.example.api.model.RecipeDifficulty;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

public record UpdateRecipeDTO(
        @NotNull Long id,
        @NotBlank @Length(max = 255) String title,
        @NotBlank String description,
        @NotNull @Min(5) Integer time,
        @NotNull @Min(1) @Max(100) Integer portions,
        URL image,
        @NotNull RecipeDifficulty difficulty
) {}
