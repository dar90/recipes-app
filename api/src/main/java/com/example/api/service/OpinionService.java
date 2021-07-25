package com.example.api.service;

import java.util.List;
import java.util.Optional;
import com.example.api.dto.UpdateOpinionDTO;
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

    public Opinion addOpinion(Opinion opinion) {
        return repository.save(opinion);
    }

    public Optional<Opinion> putOpinion(UpdateOpinionDTO opinionDto) {
        Optional<Opinion> optionalOpinion = repository.findById(opinionDto.id());

        if(optionalOpinion.isEmpty()) {
            return Optional.empty();
        }

        Opinion opinion = optionalOpinion.get();
        opinion.setContent(opinionDto.content());
        opinion.setCreated(opinionDto.created());
        opinion.setGrade(opinionDto.grade());
        opinion = repository.save(opinion);
        return Optional.of(opinion);
    }

    public Optional<Opinion> deleteOpinion(Long id) {
        repository.deleteById(id);
        return Optional.empty();
    }
}
