package com.example.api.model;

import java.net.URL;
import java.util.List;
import java.util.Map;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;
    private String title;
    private String description;
    private Integer time;
    private Integer portions;
    private URL image;
    private RecipeDifficulty difficulty;

    @OneToMany(mappedBy = "author")
    private List<Opinion> opinions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany(targetEntity = Ingredient.class)
    private Map<Ingredient, Double> ingredients;

    @ManyToMany
    private List<Category> categories;
}