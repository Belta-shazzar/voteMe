package com.shazzar.voteme.entity.email.confirmationtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long> {
    @Query("select c from ConfirmationToken c where c.token = ?1")
    ConfirmationToken getByToken(String confirmationToken);
}
