package com.vicsergeev.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Created by Victor 16.10.2025
 */

public record CreatedNotificationRequest(
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email
) {}
