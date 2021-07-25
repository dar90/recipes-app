package com.example.api.controller;

import java.util.List;
import com.example.api.dto.UpdateCategoryDTO;
import com.example.api.model.Category;
import com.example.api.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategory(@PathVariable String name) {
        return ResponseEntity.of(service.getCategory(name));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(service.addCategory(category));
    }

    @PutMapping
    public ResponseEntity<Category> putCategory(@Valid @RequestBody UpdateCategoryDTO categoryDto) {
        return ResponseEntity.of(service.putCategory(categoryDto));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Category> deleteCategory(@PathVariable String name) {
        return ResponseEntity.of(service.deleteCategory(name));
    }
}
