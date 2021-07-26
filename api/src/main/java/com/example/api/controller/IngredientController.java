package com.example.api.controller;

import java.util.List;

import com.example.api.dto.UpdateIngredientDTO;
import com.example.api.model.Ingredient;
import com.example.api.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    
    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id) {
        return ResponseEntity.of(service.getIngredient(id));
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(service.getAllIngredients());
    }

    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(service.addIngrednient(ingredient));
    }

    @PutMapping
    public ResponseEntity<Ingredient> putIngredient(@Valid @RequestBody UpdateIngredientDTO ingredientDto) {
        return ResponseEntity.of(service.putIngredient(ingredientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        service.deleteIngredient(id);
        return ResponseEntity.ok().build();
    }
}
