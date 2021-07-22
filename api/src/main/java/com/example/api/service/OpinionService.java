package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.model.Opinion;
import com.example.api.repository.OpinionRepository;

import org.springframework.stereotype.Service;

@Service
public class OpinionService {
    
    private final OpinionRepository repository;

    public OpinionService(OpinionRepository repository) {
        this.repository = repository;
    }

    public Optional<Opinion> getOpinion(Long id) {
        return repository.findById(id);
    }

    public List<Opinion> getAllOpinions() {
        return repository.findAll();
    }

}
