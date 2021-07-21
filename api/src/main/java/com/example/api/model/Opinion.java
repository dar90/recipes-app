package com.example.api.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;

@Data
@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "Field 'opinion_id' cannot be null.")
    @Column(name = "opinion_id")
    private Long id;

    @Size(max = 255, message = "Field 'content' must be between 0 and 255 characters.")
    private String content;

    @NotBlank(message = "Field 'created' cannot be null.")
    private LocalDateTime created;

    @NotBlank(message = "Field 'grade' cannot be null.")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}