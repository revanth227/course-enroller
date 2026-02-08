package com.example.CourseEnroller.course;

import com.example.CourseEnroller.course.dto.CourseDto;
import com.example.CourseEnroller.exception.ResourceNotFoundException;
import com.example.CourseEnroller.search.MatchDetail;
import com.example.CourseEnroller.search.SearchResultDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    @Transactional()
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            CourseDto courseDto = courseToDto(course);
            courseDtoList.add(courseDto);
        }

        return courseDtoList;
    }

    private CourseDto courseToDto(Course course) {
        int topicCount = 0;
        int subTopicCount = 0;

        if (course.getTopicList() != null) {
            topicCount = course.getTopicList().size();

            for (Topic topic : course.getTopicList()) {
                if (topic.getSubTopicList() != null) {
                    subTopicCount += topic.getSubTopicList().size();
                }
            }
        }

        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                (long) topicCount,
                (long) subTopicCount
        );
    }

    public @Nullable Course courseById(String courseId) {

        Optional<Course> byId = courseRepo.findById(courseId);
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("No course Found");
        }
        return byId.get();
    }

    public List<SearchResultDTO> search(String q) {
        String lowerQuery = q.toLowerCase();
        List<Course> courses = courseRepo.findAll();
        return courses.stream()
                .map(course -> {
                    List<MatchDetail> matches = new ArrayList<>();
                    if (course.getTitle().toLowerCase().contains(lowerQuery) ||
                            course.getDescription().toLowerCase().contains(lowerQuery)) {
                        matches.add(new MatchDetail(
                                "course",
                                null,
                                null,
                                course.getTitle(),
                                createSnippet(course.getDescription(), lowerQuery)
                        ));
                    }

                    for (Topic t : course.getTopicList()) {
                        if (t.getTopicTitle().toLowerCase().contains(lowerQuery)) {
                            matches.add(new MatchDetail(
                                    "topic",
                                    t.getTopicTitle(),
                                    null,
                                    t.getTopicTitle(),
                                    "Matched in topic title"
                            ));
                        }
                        for (SubTopic st : t.getSubTopicList()) {
                            boolean titleMatch =
                                    st.getTitle().toLowerCase().contains(lowerQuery);
                            boolean contentMatch =
                                    st.getContent().toLowerCase().contains(lowerQuery);
                            if (titleMatch || contentMatch) {
                                String snippet = createSnippet(st.getContent(), lowerQuery);
                                matches.add(new MatchDetail(
                                        "subtopic",
                                        t.getTopicTitle(),
                                        st.getId(),
                                        st.getTitle(),
                                        snippet
                                ));
                            }
                        }
                    }
                    if (matches.isEmpty()) return null;
                    SearchResultDTO dto = new SearchResultDTO();
                    dto.setCourseId(course.getId());
                    dto.setCourseTitle(course.getTitle());
                    dto.setMatches(matches);
                    return dto;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String createSnippet(String content, String query) {
        int index = content.toLowerCase().indexOf(query);
        if (index == -1)
            return content.substring(0, Math.min(content.length(), 100)) + "...";

        int start = Math.max(0, index - 30);
        int end = Math.min(content.length(), index + query.length() + 60);
        return "..." + content.substring(start, end) + "...";
    }
}
