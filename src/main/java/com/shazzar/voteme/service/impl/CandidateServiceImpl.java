package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.Candidate;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.repository.CandidateRepo;
import com.shazzar.voteme.service.CandidateService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {
    
    private final CandidateRepo repo;
    private final PositionServiceImpl positionService;
    private static final String NOT_FOUND_ERROR_MSG = "%s with %s %s, not found";

    public CandidateServiceImpl(CandidateRepo repo, PositionServiceImpl positionService) {
        this.repo = repo;
        this.positionService = positionService;
    }


    @SneakyThrows
    @Override
    public Candidate getById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "Candidate", "id", id)));
    }

    @Override
    public void addCandidate(Long positionId, User user) {
        Candidate candidate = new Candidate();
        candidate.setId(user.getId());
        candidate.setCandidateFullName(user.getFullName());
        Position position = positionService.getPositionById(positionId);
        candidate.setEvent(user.getEvent());
        candidate.setPosition(position);
        repo.save(candidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        repo.save(candidate);
    }
}
