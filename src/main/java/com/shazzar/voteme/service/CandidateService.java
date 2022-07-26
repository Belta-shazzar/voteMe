package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.User;

public interface CandidateService {
    void addCandidate(Long positionId, User user);
}
