package com.example.CourseEnroller.progress;

import lombok.Data;

import java.time.Instant;

@Data
public class ProgressDto {
    private String title;
    private boolean completed;
    private Instant completedAt;
}
