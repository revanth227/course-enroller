package com.example.CourseEnroller.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchDetail {
    private String type;
    private String topicTitle;
    private String subtopicId;
    private String subtopicTitle;
    private String snippet;
}