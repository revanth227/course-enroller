package com.example.CourseEnroller.entrollment;

import com.example.CourseEnroller.auth.Users;
import com.example.CourseEnroller.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepo extends JpaRepository<Enrollment,Long> {

        Optional<Enrollment> findByUser(Users user);

        boolean existsByUserAndCourse(Users user, Course course);

       // Optional<Enrollment> findById(Long id);
}


