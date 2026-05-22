package com.duoc.enrollmentplatform.courses.tests.unit;

import com.duoc.enrollmentplatform.courses.application.CreateCourseRequest;
import com.duoc.enrollmentplatform.courses.application.CreateCourseUseCase;
import com.duoc.enrollmentplatform.courses.application.CourseDTO;
import com.duoc.enrollmentplatform.courses.domain.repositories.InMemoryCourseRepository;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateCourseUseCaseTest {
    private CreateCourseRequest request(String name, String instructor, int hours, double price) {
        CreateCourseRequest r = new CreateCourseRequest();
        r.name = name; r.instructor = instructor; r.durationHours = hours; r.price = price;
        return r;
    }

    @Test void persistsNewCourseAndReturnsDTO() {
        InMemoryCourseRepository repo = new InMemoryCourseRepository();
        CourseDTO dto = new CreateCourseUseCase(repo).execute(request("Bases de datos", "Carlos Pérez", 30, 120000));
        assertNotNull(dto.id); assertEquals("Bases de datos", dto.name); assertEquals(1, repo.findAll().size());
    }
    @Test void rejectsNegativePrice() {
        assertEquals(DomainError.Type.VALIDATION,
                assertThrows(DomainError.class, () -> new CreateCourseUseCase(new InMemoryCourseRepository())
                        .execute(request("Curso", "Prof", 10, -500))).getType());
    }
    @Test void rejectsBlankCourseName() {
        assertThrows(DomainError.class, () -> new CreateCourseUseCase(new InMemoryCourseRepository())
                .execute(request("", "Prof", 10, 100000)));
    }
}
