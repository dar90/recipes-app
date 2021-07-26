package com.example.api.model;

import java.net.URL;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @NotBlank(message = "Field 'name' cannot be null.")
    @Length(max = 100, message = "Field 'name' shouldn't be greater than 100 signs.")
    private String name;

    @NotBlank(message = "Field 'description' cannot be null.")
    @Length(max = 255, message = "Field 'name' shouldn't be greater than 255 signs.")
    private String description;

    private URL image;

    @JsonIgnoreProperties({"categories", "author", "opinions", "ingredients", "description"})
    @ManyToMany
    private List<Recipe> recipes;
}