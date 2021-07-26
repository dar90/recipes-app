package com.example.api.model;

import java.net.URL;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "recipe_id")
    private Long id;

    @NotBlank(message = "Field 'title' cannot be null.")
    @Length(max = 255, message = "Field 'title' shouldn't be greater than 255 signs.")
    private String title;

    @NotBlank(message = "Field 'description' cannot be null.")
    @Column(name = "description", columnDefinition = "TEXT NOT NULL")
    private String description;

    @NotNull(message = "Field 'time' cannot be null.")
    @Min(5)
    private Integer time;

    @NotNull(message = "Field 'portions' cannot be null.")
    @Min(1)
    @Max(100)
    private Integer portions;

    private URL image;

    @NotNull(message = "Field 'difficulty' cannot be null.")
    private RecipeDifficulty difficulty;

    @JsonIgnoreProperties("recipe")
    @OneToMany(mappedBy = "recipe")
    private List<Opinion> opinions;

    @JsonIgnoreProperties({"recipes", "opinions"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @JsonIgnoreProperties("recipe")
    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @ManyToMany(mappedBy = "recipes")
    @JsonIgnoreProperties("recipes")
    private List<@NotNull Category> categories;
}