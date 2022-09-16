package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.Candidate;
import com.shazzar.voteme.entity.User;

public interface CandidateService {

    Candidate getById(Long id);
    void addCandidate(Long positionId, User user);
    void deleteCandidate(Long id);

    void saveCandidate(Candidate candidate);
}
