package com.example.api.model;

import java.net.URL;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    private String name;
    private String description;
    private URL image;

    @ManyToMany
    private List<Recipe> recipes;
}