package com.example.CourseEnroller.progress;

import lombok.Data;

import java.util.List;

@Data
public class ProgressDto2 {
    private Long id;
    private String courseId;
    private String courseTitle;
    private long totalSubtopics;
    private long completedSubtopics;
    private double completionPercentage;

    private List<CompletedItemDto> completedItems;
}


