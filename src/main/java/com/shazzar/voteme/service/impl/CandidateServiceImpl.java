package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.Candidate;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.repository.CandidateRepo;
import com.shazzar.voteme.service.CandidateService;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {
    
    private final CandidateRepo repo;
    private final PositionServiceImpl positionService;

    public CandidateServiceImpl(CandidateRepo repo, PositionServiceImpl positionService) {
        this.repo = repo;
        this.positionService = positionService;
    }

    @Override
    public void addCandidate(Long positionId, User user) {
        Candidate candidate = new Candidate();
        candidate.setId(user.getId());
        candidate.setCandidateFullName(user.getFullName());
        Position position = positionService.getPositionById(positionId);
        candidate.setPosition(position);
        repo.save(candidate);
    }
}
