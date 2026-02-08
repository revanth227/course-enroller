package com.example.CourseEnroller.seeddata;

import lombok.Data;

import java.util.List;

@Data
public class SeedCourse {
    private String id;
    private String title;
    private String description;
    private List<SeedTopic> topics;
}
