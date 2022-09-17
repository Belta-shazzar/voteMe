package com.shazzar.voteme.entity.email.confirmationtoken;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.email.EmailService;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo repo;
    private final EmailService emailService;
    private final UserServiceImpl userService;

    @Lazy
    public ConfirmationTokenService(ConfirmationTokenRepo repo, EmailService emailService, UserServiceImpl userService) {
        this.repo = repo;
        this.emailService = emailService;
        this.userService = userService;
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

    @SneakyThrows
    @Transactional
    public String confirmToken(String confirmationToken) {
        ConfirmationToken token = repo.getByToken(confirmationToken).orElseThrow(() ->
                new ResourceNotFoundException("Token does not exist"));

        LocalDateTime expiryTime = token.getExpiresAt();

        if (expiryTime.isBefore(LocalDateTime.now())) {
            repo.delete(token);
            userService.deleteUser(token.getUser());
            throw new IllegalStateException("token expired. Signup again");
//            TODO: Create new confirmation token
        } else {
            String isEnabledResult = userService.updateIsEnabled(token.getUser());
            repo.delete(token);
            return isEnabledResult;
        }
    }
}
