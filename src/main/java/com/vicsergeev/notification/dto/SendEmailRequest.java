package com.vicsergeev.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendEmailRequest(
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email,
    
    @NotBlank(message = "Subject is required")
    String subject,
    
    @NotBlank(message = "Text is required")
    String text
) {}
