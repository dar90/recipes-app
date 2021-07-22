package com.example.api.controller;

import java.util.List;

import com.example.api.model.Category;
import com.example.api.service.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategory(@PathVariable("name") String name) {
        return ResponseEntity.of(service.getCategory(name));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

}
