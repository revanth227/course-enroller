package com.example.CourseEnroller.entrollment;

import com.example.CourseEnroller.auth.UserRepo;
import com.example.CourseEnroller.auth.Users;
import com.example.CourseEnroller.course.Course;
import com.example.CourseEnroller.course.CourseRepo;
import com.example.CourseEnroller.exception.ConflictException;
import com.example.CourseEnroller.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepo enrollmentRepo;
    private final CourseRepo courseRepo;
    private final UserRepo userRepo;

    public EnrollmentDto newEnrollment(String courseId) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = userRepo.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        boolean alreadyEnrolled = enrollmentRepo.existsByUserAndCourse(user, course);

        if (alreadyEnrolled) {
            throw new ConflictException("You are already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollmentRepo.save(enrollment);
        return enrollmentDto(enrollment);
    }

    private EnrollmentDto enrollmentDto(Enrollment enrollment) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(enrollment.getId());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseTitle(enrollment.getCourse().getTitle());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }

}
