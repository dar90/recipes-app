package com.example.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.api.dto.UpdateRecipeDTO;
import com.example.api.model.Category;
import com.example.api.model.Recipe;
import com.example.api.model.User;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.RecipeRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeService {
    
    private final RecipeRepository repository;
    private final CategoryRepository categoryRepository;

    public RecipeService(RecipeRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<Recipe> getRecipe(Long id) {
        return repository.findById(id);
    }

    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    public Recipe addRecipe(Recipe recipe) {
        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setAuthor(currentUser);
        
        List<Category> categories = recipe.getCategories()
                                            .stream()
                                            .<Optional<Category>>map(category -> categoryRepository.findById(category.getName()))
                                            .filter(Optional::isPresent)
                                            .<Category>map(Optional::get)
                                            .toList();
        recipe.setCategories(categories);
        
        Recipe recipeEntity = repository.save(recipe);
        categories.forEach(category -> category.getRecipes().add(recipeEntity));
        categoryRepository.saveAll(categories);

        return recipeEntity;
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

    public void deleteRecipe(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Optional<Recipe> updateRecipeCategories(Long recipeId, List<Category> categories) {
        Optional<Recipe> optionalRecipe = repository.findById(recipeId);

        if(optionalRecipe.isEmpty())
            return Optional.empty();

        Recipe recipe = optionalRecipe.get();
        recipe.getCategories().forEach(
            category -> category.getRecipes().remove(recipe)
        );
        categoryRepository.saveAll(recipe.getCategories());

        List<Category> updatedCategories = categories.stream()
                            .<Optional<Category>>map(category -> categoryRepository.findById(category.getName()))
                            .filter(Optional::isPresent)
                            .<Category>map(Optional::get)
                            .toList();
        recipe.setCategories(new ArrayList<>(updatedCategories));
        Recipe recipeEntity = repository.save(recipe);

        updatedCategories.forEach(category -> category.getRecipes().add(recipeEntity));
        categoryRepository.saveAll(updatedCategories);

        return Optional.of(recipeEntity);
    }

}
