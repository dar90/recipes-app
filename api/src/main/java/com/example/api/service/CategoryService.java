package com.example.api.service;

import java.util.List;
import java.util.Optional;
import com.example.api.dto.UpdateCategoryDTO;
import com.example.api.model.Category;
import com.example.api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<Category> getCategory(String name) {
        return repository.findById(name);
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category addCategory(Category category) {
        return repository.save(category);
    }

    public Optional<Category> putCategory(UpdateCategoryDTO categoryDto) {
        Optional<Category> optionalCategory = repository.findById(categoryDto.name());

        if(optionalCategory.isEmpty()) {
            return Optional.empty();
        }

        Category category = optionalCategory.get();
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        category.setImage(categoryDto.image());
        category = repository.save(category);
        return Optional.of(category);
    }

    public Optional<Category> deleteCategory(String name) {
        repository.deleteById(name);
        return Optional.empty();
    }
}
