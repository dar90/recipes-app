package com.example.api.model;

import java.util.List;

import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private String measureUnit;
    private List<Recipe> recipes;
}
