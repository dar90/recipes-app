package com.example.api.controller;

import java.util.List;

import com.example.api.model.Ingredient;
import com.example.api.service.IngredientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    
    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("id") Long id) {
        return ResponseEntity.of(service.getIngredient(id));
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(service.getAllIngredients());
    }

}
