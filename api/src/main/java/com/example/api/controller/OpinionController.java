package com.example.api.controller;

import java.util.List;

import com.example.api.model.Opinion;
import com.example.api.service.OpinionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/opinion")
public class OpinionController {
    
    private OpinionService service;

    public OpinionController(OpinionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Opinion>> getAllOpinions() {
        return ResponseEntity.ok(service.getAllOpinions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opinion> getOpinion(@PathVariable("id") Long id) {
        return ResponseEntity.of(service.getOpinion(id));
    }

}
