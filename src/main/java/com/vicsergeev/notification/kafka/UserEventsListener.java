package com.vicsergeev.notification.kafka;

import com.vicsergeev.notification.dto.UserEventDTO;
import com.vicsergeev.notification.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Victor 16.10.2025
 */

@Component
public class UserEventsListener {
    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(UserEventsListener.class);
    public UserEventsListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-service")
    public void onUserEvent(UserEventDTO event) {
        log.info("received user event: operation={}, email={}, name={}", event.operation(), event.email(), event.name());
        if (UserEventDTO.CREATE.equals(event.operation())) {
            emailService.sendAccountCreated(event.email());
            log.info("account created, send email to {}", event.email());
        } else if (UserEventDTO.DELETE.equals(event.operation())) {
            emailService.sendAccountDeleted(event.email());
            log.info("account deleted, send email to {}", event.email());
        } else {
            log.info("ignored event with operation {}", event.operation());
        }
    }
}


