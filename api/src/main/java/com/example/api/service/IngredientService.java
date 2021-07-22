package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.model.Ingredient;
import com.example.api.repository.IngredientRepository;

import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    
    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public Optional<Ingredient> getIngredient(Long id) {
        return repository.findById(id);
    }

    public List<Ingredient> getAllIngredients() {
        return repository.findAll();
    }

}
