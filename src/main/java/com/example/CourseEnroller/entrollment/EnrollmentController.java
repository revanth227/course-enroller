package com.example.CourseEnroller.entrollment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;


    @PostMapping("/api/courses/{courseId}/enroll")
    public EnrollmentDto createEnrollment(@PathVariable String courseId) {

        return enrollmentService.newEnrollment(courseId);
    }

}
