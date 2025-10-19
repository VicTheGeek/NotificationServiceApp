package com.vicsergeev.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreatedNotificationRequest(
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email
) {}
