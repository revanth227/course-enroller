package com.example.CourseEnroller.progress;

import java.time.Instant;

public class CompletedItemDto {

    private String subtopicId;
    private String subtopicTitle;
    private Instant completedAt;

    public CompletedItemDto() {
    }

    public CompletedItemDto(String subtopicId, String subtopicTitle, Instant completedAt) {
        this.subtopicId = subtopicId;
        this.subtopicTitle = subtopicTitle;
        this.completedAt = completedAt;
    }

    public String getSubtopicId() {
        return subtopicId;
    }

    public void setSubtopicId(String subtopicId) {
        this.subtopicId = subtopicId;
    }

    public String getSubtopicTitle() {
        return subtopicTitle;
    }

    public void setSubtopicTitle(String subtopicTitle) {
        this.subtopicTitle = subtopicTitle;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
