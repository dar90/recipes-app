package com.example.api.service;

import java.util.List;
import java.util.Optional;
import com.example.api.dto.UpdateIngredientDTO;
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

    public Ingredient addIngrednient(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    public Optional<Ingredient> putIngredient(UpdateIngredientDTO ingredientDto) {
        Optional<Ingredient> optionalIngredient = repository.findById(ingredientDto.id());

        if(optionalIngredient.isEmpty()) {
            return Optional.empty();
        }

        Ingredient ingredient = optionalIngredient.get();
        ingredient.setName(ingredientDto.name());
        ingredient.setMeasureUnit(ingredientDto.measureUnit());
        ingredient.setAmount(ingredientDto.amount());
        ingredient = repository.save(ingredient);
        return Optional.of(ingredient);
    }

    public Optional<Ingredient> deleteIngredient(Long id) {
        repository.deleteById(id);
        return Optional.empty();
    }
}
