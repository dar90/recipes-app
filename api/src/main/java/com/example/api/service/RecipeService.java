package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.model.Recipe;
import com.example.api.repository.RecipeRepository;

import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Optional<Recipe> getRecipe(Long id) {
        return repository.findById(id);
    }

    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

}
