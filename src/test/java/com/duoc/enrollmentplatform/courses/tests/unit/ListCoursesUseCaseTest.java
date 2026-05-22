package com.duoc.enrollmentplatform.courses.tests.unit;

import com.duoc.enrollmentplatform.courses.application.CourseDTO;
import com.duoc.enrollmentplatform.courses.application.ListCoursesUseCase;
import com.duoc.enrollmentplatform.courses.domain.entities.Course;
import com.duoc.enrollmentplatform.courses.domain.repositories.InMemoryCourseRepository;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ListCoursesUseCaseTest {
    @Test void returnsEmptyCatalogWhenNoCourses() {
        assertTrue(new ListCoursesUseCase(new InMemoryCourseRepository()).execute().isEmpty());
    }
    @Test void returnsAllAvailableCourses() {
        var courses = List.of(Course.create(Id.generate(), "Cloud Native", "Ana Ruiz", 25, Money.create(180000)));
        assertEquals(1, new ListCoursesUseCase(new InMemoryCourseRepository(courses)).execute().size());
    }
    @Test void returnsDetailedCourseInformation() {
        var course = Course.create(Id.create("c-1"), "Introducción a Java", "María González", 40, Money.create(150000));
        CourseDTO dto = new ListCoursesUseCase(new InMemoryCourseRepository(List.of(course))).execute().get(0);
        assertEquals("c-1", dto.id); assertEquals("Introducción a Java", dto.name);
        assertEquals("María González", dto.instructor); assertEquals(40, dto.durationHours); assertEquals(150000.0, dto.price);
    }
}
