package com.duoc.enrollmentplatform.courses.domain.repositories;

import com.duoc.enrollmentplatform.courses.domain.entities.Course;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

import java.util.*;

public class InMemoryCourseRepository implements CourseRepository {
    private final Map<String, Course> courses = new HashMap<>();

    public InMemoryCourseRepository() {}
    public InMemoryCourseRepository(List<Course> initial) {
        initial.forEach(c -> courses.put(c.getId().getValue(), c));
    }

    @Override public void save(Course course) { courses.put(course.getId().getValue(), course); }
    @Override public Optional<Course> findById(Id id) { return Optional.ofNullable(courses.get(id.getValue())); }
    @Override public List<Course> findAll() { return new ArrayList<>(courses.values()); }
}
