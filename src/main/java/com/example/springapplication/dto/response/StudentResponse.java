package com.example.springapplication.dto.response;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public record StudentResponse(
    Long id,
    String name,
    String email,
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dob,
    List<CourseResponse> courses
) {
    // Record cho danh sách student (không bao gồm courses để tránh N+1)
    public record Simple(
        Long id,
        String name,
        String email,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dob
    ) {}
}
