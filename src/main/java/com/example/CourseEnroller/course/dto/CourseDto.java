package com.example.CourseEnroller.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDto {
    private String id;
    private String title;
    private String description;
    private Long topicCount;
    private Long subTopicCount;
}
