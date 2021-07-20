package com.example.api.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity
public class Ingredient {
    @Id
    @NotBlank(message = "Field 'name' cannot be null.")
    @Size(max = 255, message = "Field 'name' shouldn't be greater than 255 signs.")
    private String name;

    @NotBlank(message = "Field 'measure_unit' cannot be null.")
    @Size(max = 100, message = "Field 'measure_unit' shouldn't be greater than 100 signs.")
    private String measureUnit;

    @ManyToMany
    private List<@NotEmpty Recipe> recipes;
}