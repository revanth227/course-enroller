package com.example.CourseEnroller.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, String> {

}
