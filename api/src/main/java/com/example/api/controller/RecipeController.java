package com.example.api.controller;

import java.util.List;
import com.example.api.dto.UpdateRecipeDTO;
import com.example.api.model.Category;
import com.example.api.model.Recipe;
import com.example.api.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        return ResponseEntity.of(service.getRecipe(id));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(service.getAllRecipes());
    }

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe) {
        return ResponseEntity.ok(service.addRecipe(recipe));
    }

    @PutMapping
    public ResponseEntity<Recipe> putRecipe(@Valid @RequestBody UpdateRecipeDTO recipeDto) {
        return ResponseEntity.of(service.putRecipe(recipeDto));
    }

    @PatchMapping("/{id}/categories")
    public ResponseEntity<Recipe> updateRecipeCategories(@PathVariable("id") Long id, 
                                                        @RequestBody List<Category> categories) {
        return ResponseEntity.of(service.updateRecipeCategories(id, categories));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        service.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }

}
