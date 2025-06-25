package com.example.springapplication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record StudentRequest(
    @NotBlank(message = "Name is required")
    String name,
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dob
) {}
