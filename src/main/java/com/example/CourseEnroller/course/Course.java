package com.example.CourseEnroller.course;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity

@Data
public class Course {
    @Id
    @Column(length = 150)
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Topic> topicList = new ArrayList<>();

    public void addTopic(Topic topic) {
        this.topicList.add(topic);
        topic.setCourse(this);
    }
}
