package com.example.api.controller;

import java.util.List;
import com.example.api.dto.UpdateOpinionDTO;
import com.example.api.model.Opinion;
import com.example.api.service.OpinionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/opinion")
public class OpinionController {
    
    private final OpinionService service;

    public OpinionController(OpinionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Opinion>> getAllOpinions() {
        return ResponseEntity.ok(service.getAllOpinions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opinion> getOpinion(@PathVariable Long id) {
        return ResponseEntity.of(service.getOpinion(id));
    }

    @PostMapping
    public ResponseEntity<Opinion> addOpinion(@RequestBody Opinion opinion) {
        return ResponseEntity.ok(service.addOpinion(opinion));
    }

    @PutMapping
    public ResponseEntity<Opinion> putOpinion(@Valid @RequestBody UpdateOpinionDTO opinionDto) {
        return ResponseEntity.of(service.putOpinion(opinionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Opinion> deleteOpinion(@PathVariable Long id) {
        return ResponseEntity.of(service.deleteOpinion(id));
    }
}
