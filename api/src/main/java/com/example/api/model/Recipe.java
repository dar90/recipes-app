package com.example.api.model;

import java.net.URL;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Recipe {
    private Long id;
    private String title;
    private String description;
    private Integer time;
    private Integer portions;
    private URL image;
    private User author;
    private RecipeDifficulty difficulty;
    private List<Opinion> opinions;
    private Map<Ingredient, Double> ingredients;
    private List<Category> categories;
}
