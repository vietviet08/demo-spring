package com.example.springapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRequest(
    @NotBlank(message = "Title is required")
    String title,
    
    String description,
    
    @NotNull(message = "Teacher ID is required")
    Long teacherId
) {}
