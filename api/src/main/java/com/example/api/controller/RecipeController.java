package com.example.api.controller;

import java.util.List;

import com.example.api.model.Recipe;
import com.example.api.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") Long id) {
        return ResponseEntity.of(service.getRecipe(id));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(service.getAllRecipes());
    }

}
