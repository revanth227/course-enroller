package com.example.CourseEnroller.entrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {
    private Long id;
    private String courseId;
    private String courseTitle;
    private Instant enrolledAt;
}
