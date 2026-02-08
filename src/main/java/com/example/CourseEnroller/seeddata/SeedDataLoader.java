package com.example.CourseEnroller.seeddata;

import com.example.CourseEnroller.course.Course;
import com.example.CourseEnroller.course.CourseRepo;
import com.example.CourseEnroller.course.SubTopic;
import com.example.CourseEnroller.course.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
//import tools.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class SeedDataLoader implements CommandLineRunner {

    private final CourseRepo courseRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        if (courseRepository.count() > 0) {
            return;
        }

        InputStream is = new ClassPathResource("data/courses.json").getInputStream();

        SeedCoursesWrapper wrapper =
                objectMapper.readValue(is, SeedCoursesWrapper.class);

        for (SeedCourse sc : wrapper.getCourses()) {
            Course course = mapCourse(sc);
            courseRepository.save(course);
        }
    }

    private Course mapCourse(SeedCourse sc) {
        Course course = new Course();
        course.setId(sc.getId());
        course.setTitle(sc.getTitle());
        course.setDescription(sc.getDescription());

        int topicOrder = 0;
        for (SeedTopic st : sc.getTopics()) {
            Topic topic = new Topic();
            topic.setTopicId(st.getId());
            topic.setTopicTitle(st.getTitle());
            topic.setCourse(course);

            int subOrder = 0;
            for (SeedSubtopic ss : st.getSubtopics()) {
                SubTopic sub = new SubTopic();
                sub.setId(ss.getId());
                sub.setTitle(ss.getTitle());
                sub.setContent(ss.getContent());
                sub.setOrderIndex(subOrder++);
                sub.setTopic(topic);

                topic.getSubTopicList().add(sub);
            }

            course.getTopicList().add(topic);
        }

        return course;
    }

}
