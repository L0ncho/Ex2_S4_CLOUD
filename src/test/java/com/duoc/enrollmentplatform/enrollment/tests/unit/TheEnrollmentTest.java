package com.duoc.enrollmentplatform.enrollment.tests.unit;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Enrollment;
import com.duoc.enrollmentplatform.enrollment.domain.entities.EnrollmentLine;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TheEnrollmentTest {
    @Test void calculatesTotalFromSingleCourse() {
        var lines = List.of(EnrollmentLine.create(Id.generate(), Id.generate(), "Intro Java", Money.create(150000)));
        assertEquals(150000, Enrollment.create(Id.generate(), Id.generate(), lines).calculateTotal().getValue());
    }
    @Test void calculatesTotalFromMultipleCourses() {
        var lines = List.of(
                EnrollmentLine.create(Id.generate(), Id.generate(), "Cloud Native", Money.create(180000)),
                EnrollmentLine.create(Id.generate(), Id.generate(), "Bases de datos", Money.create(120000)));
        assertEquals(300000, Enrollment.create(Id.generate(), Id.generate(), lines).calculateTotal().getValue());
    }
    @Test void rejectsEnrollmentWithNoCourses() {
        assertEquals(DomainError.Type.VALIDATION,
                assertThrows(DomainError.class, () -> Enrollment.create(Id.generate(), Id.generate(), List.of())).getType());
    }
}
