package com.vicsergeev.notification.controller;

import com.vicsergeev.notification.dto.CreatedNotificationRequest;
import com.vicsergeev.notification.dto.DeletedNotificationRequest;
import com.vicsergeev.notification.dto.SendEmailRequest;
import com.vicsergeev.notification.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Victor 16.10.2025
 */

@Validated
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> notifyCreated(@RequestBody @Validated CreatedNotificationRequest request) {
        emailService.sendAccountCreated(request.email());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> notifyDeleted(@RequestBody @Validated DeletedNotificationRequest request) {
        emailService.sendAccountDeleted(request.email());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody @Validated SendEmailRequest request) {
        emailService.send(request.email(), request.subject(), request.text());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


