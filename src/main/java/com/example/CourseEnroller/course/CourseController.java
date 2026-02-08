package com.example.CourseEnroller.course;

import com.example.CourseEnroller.course.dto.CourseDto;
import com.example.CourseEnroller.search.SearchResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/api/courses")
    public List<CourseDto> courseList() {
        return courseService.getAllCourses();
    }

    @GetMapping("/api/courses/{courseId}")
    public ResponseEntity<Course> getTheCourseById(@PathVariable String courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.courseById(courseId));
    }

    @GetMapping("/api/search")
    public ResponseEntity<List<SearchResultDTO>> search(@RequestParam String q) {
        return ResponseEntity.ok(courseService.search(q));
    }
}
