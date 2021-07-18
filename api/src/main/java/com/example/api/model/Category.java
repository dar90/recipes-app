package com.example.api.model;

import java.net.URL;
import java.util.List;

import lombok.Data;

@Data
public class Category {
    private String name;
    private String description;
    private URL image;
    private List<Recipe> recipes;
}
