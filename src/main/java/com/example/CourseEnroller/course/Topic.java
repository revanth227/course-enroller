package com.example.CourseEnroller.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    private String topicId;
    private String topicTitle;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<SubTopic> subTopicList = new ArrayList<>();

}
