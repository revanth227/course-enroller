package com.example.CourseEnroller.progress;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProgressController {
    private final ProgressService progressService;

    @PostMapping("/api/subtopics/{subtopicId}/complete")
    public ProgressDto progress(@PathVariable String subtopicId){
        return progressService.getTheProgress( subtopicId);
    }

    @GetMapping("/api/enrollments/{enrollmentId}/progress")
    public ProgressDto2 viewProgress(@PathVariable Long enrollmentId) {
        return progressService.allProgress(enrollmentId);

    }


}
