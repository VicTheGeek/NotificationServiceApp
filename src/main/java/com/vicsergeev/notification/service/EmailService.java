package com.vicsergeev.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAccountCreated(String email) {
        log.info("sending account created email to: {}", email);
        send(email, "Уведомление о создании аккаунта", "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
    }

    public void sendAccountDeleted(String email) {
        log.info("sending account deleted email to: {}", email);
        send(email, "Уведомление об удалении аккаунта", "Здравствуйте! Ваш аккаунт был удалён.");
    }

    @CircuitBreaker(name = "mailSender", fallbackMethod = "fallbackSend")
    public void send(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("failed to send email to: {}, error: {}", to, e.getMessage(), e);
            throw e;
        }
    }

    private void fallbackSend(String to, String subject, String body, Throwable t) {
        log.warn("mailSender fallback for to={}, reason={}", to, t.toString());
        // graceful degradation - throw nothing
    }
}


