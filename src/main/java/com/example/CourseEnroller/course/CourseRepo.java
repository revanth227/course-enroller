package com.example.CourseEnroller.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, String> {
    @Query("""
            SELECT DISTINCT c FROM Course c
            LEFT JOIN c.topicList t
            LEFT JOIN t.subTopicList st
            WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(t.topicTitle) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(st.title) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    List<Course> searchCourses(@Param("query") String query);
}
