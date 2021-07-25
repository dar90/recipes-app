package com.example.api.dto;

import com.example.api.model.Grade;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record UpdateOpinionDTO(
        @NotNull Long id,
        @Length(max = 255) String content,
        @NotNull LocalDateTime created,
        @NotNull Grade grade
) {}
