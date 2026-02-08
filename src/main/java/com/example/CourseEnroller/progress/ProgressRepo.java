package com.example.CourseEnroller.progress;

import com.example.CourseEnroller.auth.Users;
import com.example.CourseEnroller.course.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgressRepo extends JpaRepository<Progress,Long> {
    Optional<Progress> findByUserAndSubTopic(Users user, SubTopic subTopic);

    boolean existsByUserAndSubTopicAndCompleted(Users user, SubTopic sub, boolean b);

}

