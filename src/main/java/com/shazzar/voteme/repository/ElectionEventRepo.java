package com.shazzar.voteme.repository;

import com.shazzar.voteme.entity.ElectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectionEventRepo extends JpaRepository<ElectionEvent, Long> {
    Optional<ElectionEvent> findByToken(String electionToken);
}
