package com.example.api.service;

import java.util.List;
import java.util.Optional;
import com.example.api.dto.UpdateRecipeDTO;
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

    public Recipe addRecipe(Recipe recipe) {
        return repository.save(recipe);
    }

    public Optional<Recipe> putRecipe(UpdateRecipeDTO recipeDto) {
        Optional<Recipe> optionalRecipe = repository.findById(recipeDto.id());

        if(optionalRecipe.isEmpty()) {
            return Optional.empty();
        }

        Recipe recipe = optionalRecipe.get();
        recipe.setTitle(recipeDto.title());
        recipe.setDescription(recipeDto.description());
        recipe.setTime(recipeDto.time());
        recipe.setPortions(recipeDto.portions());
        recipe.setImage(recipeDto.image());
        recipe.setDifficulty(recipeDto.difficulty());
        recipe = repository.save(recipe);
        return Optional.of(recipe);
    }

    public Optional<Recipe> deleteRecipe(Long id) {
        repository.deleteById(id);
        return Optional.empty();
    }
}
