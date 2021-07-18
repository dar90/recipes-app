package com.example.api.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Ingredient {
    @Id
    private String name;
    private String measureUnit;

    @ManyToMany
    private List<Recipe> recipes;
}