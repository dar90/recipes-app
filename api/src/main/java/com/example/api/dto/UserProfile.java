package com.example.api.dto;

import java.util.List;

import com.example.api.model.Opinion;
import com.example.api.model.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record UserProfile(String name, 
                        String email, 
                        @JsonIgnoreProperties("author") List<Recipe> recipes, 
                        @JsonIgnoreProperties("author") List<Opinion> opinions) {
    
}
