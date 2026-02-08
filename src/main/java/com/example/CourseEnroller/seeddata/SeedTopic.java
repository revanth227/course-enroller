package com.example.CourseEnroller.seeddata;

import lombok.Data;

import java.util.List;

@Data
public class SeedTopic {
    private String id;
    private String title;
    private List<SeedSubtopic> subtopics;
}
