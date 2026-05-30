package com.duoc.enrollmentplatform.courses.application;

import com.duoc.enrollmentplatform.courses.domain.entities.Course;
import com.duoc.enrollmentplatform.courses.domain.repositories.CourseRepository;

import java.util.List;
import java.util.Map;

public class ListCoursesUseCase {
    private final CourseRepository courseRepository;
    public ListCoursesUseCase(CourseRepository courseRepository) { this.courseRepository = courseRepository; }

    public List<CourseDTO> execute() {
        return courseRepository.findAll().stream().map(this::toDTO).toList();
    }

    private CourseDTO toDTO(Course course) {
        Map<String, Object> p = course.toPrimitives();
        return new CourseDTO((String) p.get("id"), (String) p.get("name"),
                (String) p.get("instructor"), (int) p.get("durationHours"), (double) p.get("price"));
    }
}
