package com.example.api.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Field 'opinion_id' cannot be null.")
    @Column(name = "opinion_id")
    private Long id;

    @Length(max = 255, message = "Field 'content' must be between 0 and 255 characters.")
    private String content;

    @NotNull(message = "Field 'created' cannot be null.")
    private LocalDateTime created;

    @NotNull(message = "Field 'grade' cannot be null.")
    private Grade grade;

    @JsonIgnoreProperties({"opinions", "recipes"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @JsonIgnoreProperties({"opinions", "author", "ingredients", "categories", "description"})
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}