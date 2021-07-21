package com.example.api.model;

import java.net.URL;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Field 'recipe_id' cannot be null.")
    @Column(name = "recipe_id")
    private Long id;

    @NotNull(message = "Field 'title' cannot be null.")
    @Size(max = 255, message = "Field 'title' shouldn't be greater than 255 signs.")
    private String title;

    @NotNull(message = "Field 'description' cannot be null.")
    @Size(max = 255, message = "Field 'description' shouldn't be greater than 255 signs.")
    private String description;

    @NotNull(message = "Field 'time' cannot be null.")
    @Length(max = 3)
    private Integer time;

    @NotNull(message = "Field 'portions' cannot be null.")
    @Size(min = 2, max = 10, message = "Field 'portions' might contain numbers between 2 and 10.")
    private Integer portions;

    private URL image;

    @NotNull(message = "Field 'difficulty' cannot be null.")
    private RecipeDifficulty difficulty;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("recipe")
    private List<Opinion> opinions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("recipes")
    private User author;

    @ManyToMany(targetEntity = Ingredient.class)
    private Map<@NotNull Ingredient, Double> ingredients;

    @ManyToMany
    private List<@NotNull Category> categories;
}