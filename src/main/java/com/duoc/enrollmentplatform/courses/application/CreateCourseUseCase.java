package com.duoc.enrollmentplatform.courses.application;

import com.duoc.enrollmentplatform.courses.domain.entities.Course;
import com.duoc.enrollmentplatform.courses.domain.repositories.CourseRepository;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;

import java.util.Map;

public class CreateCourseUseCase {
    private final CourseRepository courseRepository;
    public CreateCourseUseCase(CourseRepository courseRepository) { this.courseRepository = courseRepository; }

    public CourseDTO execute(CreateCourseRequest request) {
        Course course = Course.create(Id.generate(), request.name, request.instructor,
                request.durationHours, Money.create(request.price));
        courseRepository.save(course);
        Map<String, Object> p = course.toPrimitives();
        return new CourseDTO((String) p.get("id"), (String) p.get("name"),
                (String) p.get("instructor"), (int) p.get("durationHours"), (double) p.get("price"));
    }
}
