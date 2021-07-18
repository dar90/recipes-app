package com.example.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opinion_id")
    private Long id;
    private String content;
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    // private Recipe recipe;
    private Grade grade;
}
