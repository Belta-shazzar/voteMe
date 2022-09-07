package com.shazzar.voteme.entity.email;

public interface EmailService {
    void send(String toEmail, String subject, String emailUI);

    String buildEmail(String name, String link);
}
