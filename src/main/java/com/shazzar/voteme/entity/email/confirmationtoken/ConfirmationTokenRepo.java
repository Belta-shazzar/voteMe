package com.shazzar.voteme.entity.email.confirmationtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long> {
    @Query("select c from ConfirmationToken c where c.token = ?1")
    Optional<ConfirmationToken> getByToken(String confirmationToken);
}
