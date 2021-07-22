package com.example.api.service;

import java.util.List;
import java.util.Optional;

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

}
