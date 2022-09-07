package com.shazzar.voteme.entity.email.confirmationtoken;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.email.EmailService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo repo;
    private final EmailService emailService;

    public ConfirmationTokenService(ConfirmationTokenRepo repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    public String createToken(User user) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, user);

        repo.save(confirmationToken);

        String confirmationLink = "http://localhost:8080/voteMe/v1/confirmationToken/confirm?token=" + token;
        String mailSubject = "(VoteMe) Email Verification";
        emailService.send(user.getEmail(), mailSubject, emailService.buildEmail(user.getFullName(), confirmationLink));
        return "Account created successfully. Kindly click the link sent to your mail to confirm your account";
    }
}
