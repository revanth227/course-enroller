package com.example.CourseEnroller.progress;

import com.example.CourseEnroller.auth.UserRepo;
import com.example.CourseEnroller.auth.Users;
import com.example.CourseEnroller.course.*;
import com.example.CourseEnroller.entrollment.Enrollment;
import com.example.CourseEnroller.entrollment.EnrollmentRepo;
import com.example.CourseEnroller.exception.BadRequestException;
import com.example.CourseEnroller.exception.ForbiddenException;
import com.example.CourseEnroller.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProgressService {
    private final CourseRepo courseRepo;
    private final UserRepo userRepo;
    private final SubtopicRepo subtopicRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final ProgressRepo pro;


    @Transactional
    public ProgressDto getTheProgress(String subtopicId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        Optional<SubTopic> subTopic = subtopicRepo.findById(subtopicId);
        if (subTopic.isEmpty()) {
            throw new ResourceNotFoundException("No subTopic Found");
        }
        Course course = subTopic.get().getTopic().getCourse();
        boolean alreadyEnrolled = enrollmentRepo.existsByUserAndCourse(user, course);

        if (!alreadyEnrolled) {
            throw new ForbiddenException("You are not enrolled in this course");
        }
        Optional<Progress> existingProgress = pro.findByUserAndSubTopic(user, subTopic.get());

        if (existingProgress.isPresent() && existingProgress.get().isCompleted()) {
            return convertToDto(existingProgress.get());
        }

        Progress progress = existingProgress.orElse(new Progress());
        progress.setUser(user);
        progress.setSubTopic(subTopic.get());
        progress.setCompleted(true);
        progress.setCompletedAt(Instant.now());

        pro.save(progress);

        return convertToDto(progress);
    }

    private ProgressDto convertToDto(Progress progress) {
        ProgressDto progressDto = new ProgressDto();
        progressDto.setTitle(progress.getSubTopic().getTitle());
        progressDto.setCompleted(true);
        progressDto.setCompletedAt(progress.getCompletedAt());
        return progressDto;
    }

    public ProgressDto2 allProgress(Long enrollmentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Enrollment enrollment = enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() -> new ForbiddenException("No Enrollment"));

        if (!enrollment.getUser().equals(user)) {
            throw new BadRequestException("Access Denied: This is not your enrollment");
        }

        return getTheDtoFromEnrollment(enrollment, user);
    }

    private ProgressDto2 getTheDtoFromEnrollment(Enrollment enrollment, Users user) {

        Course course = enrollment.getCourse();

        long totalSubtopics = 0;
        long completedCount = 0;

        List<CompletedItemDto> completedItems = new ArrayList<>();

        for (Topic topic : course.getTopicList()) {
            for (SubTopic sub : topic.getSubTopicList()) {

                totalSubtopics++;

                Optional<Progress> progress =
                        pro.findByUserAndSubTopic(user, sub);

                if (progress.isPresent() && progress.get().isCompleted()) {

                    completedCount++;

                    CompletedItemDto item = new CompletedItemDto();
                    item.setSubtopicId(sub.getId());
                    item.setSubtopicTitle(sub.getTitle());
                    item.setCompletedAt(progress.get().getCompletedAt());

                    completedItems.add(item);
                }
            }
        }

        double percentage = 0.0;
        if (totalSubtopics > 0) {
            percentage = ((double) completedCount / totalSubtopics) * 100;
            percentage = BigDecimal.valueOf(percentage)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        ProgressDto2 dto2 = new ProgressDto2();
        dto2.setId(enrollment.getId());
        dto2.setCourseId(course.getId());
        dto2.setCourseTitle(course.getTitle());
        dto2.setTotalSubtopics(totalSubtopics);
        dto2.setCompletedSubtopics(completedCount);
        dto2.setCompletionPercentage(percentage);
        dto2.setCompletedItems(completedItems);

        return dto2;
    }


}
