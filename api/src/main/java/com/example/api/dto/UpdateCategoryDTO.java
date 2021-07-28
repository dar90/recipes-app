package com.example.api.dto;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import java.net.URL;

public record UpdateCategoryDTO(
    @NotBlank @Length(max = 100) String name,
    @NotBlank @Length(max = 255) String description,
    URL image
) {}