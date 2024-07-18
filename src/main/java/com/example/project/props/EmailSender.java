package com.example.project.props;

public interface EmailSender {
    void sendEmail(String to, String subject, String message);
}
