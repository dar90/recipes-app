package com.example.api.model;

import java.net.URL;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    @NotBlank(message = "Field 'name' cannot be null.")
    @Size(max = 100, message = "Field 'name' shouldn't be greater than 100 signs.")
    private String name;

    @NotBlank(message = "Field 'description' cannot be null.")
    @Size(max = 255, message = "Field 'name' shouldn't be greater than 255 signs.")
    private String description;

    private URL image;

    @ManyToMany
    private List<Recipe> recipes;
}