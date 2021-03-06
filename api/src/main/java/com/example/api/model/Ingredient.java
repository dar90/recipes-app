package com.example.api.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.Length;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Field 'name' cannot be null.")
    @Length(max = 255, message = "Field 'name' shouldn't be greater than 255 signs.")
    private String name;

    @NotBlank(message = "Field 'measureUnit' cannot be null.")
    @Length(max = 100, message = "Field 'measureUnit' shouldn't be greater than 100 signs.")
    private String measureUnit;

    @JsonIgnoreProperties({"ingredients", "opinions", "author", "categories", "description"})
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @NotNull
    private Double amount;
}