package com.example.CourseEnroller.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDTO {
    private String courseId;
    private String courseTitle;
    private List<MatchDetail> matches;
}