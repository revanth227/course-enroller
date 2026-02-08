package com.example.CourseEnroller.progress;

import com.example.CourseEnroller.auth.Users;
import com.example.CourseEnroller.course.SubTopic;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtopic_id")
    private SubTopic subTopic;

    private boolean completed = false;

    private Instant completedAt;
}
