package com.duoc.enrollmentplatform.courses.tests.unit;

import com.duoc.enrollmentplatform.courses.domain.entities.Course;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TheCourseTest {
    @Test void createsWithValidAttributes() { assertNotNull(Course.create(Id.generate(), "Cloud Native", "Ana Ruiz", 25, Money.create(180000))); }
    @Test void rejectsEmptyName() { assertEquals(DomainError.Type.VALIDATION, assertThrows(DomainError.class, () -> Course.create(Id.generate(), "", "Ana", 25, Money.create(180000))).getType()); }
    @Test void rejectsEmptyInstructor() { assertEquals(DomainError.Type.VALIDATION, assertThrows(DomainError.class, () -> Course.create(Id.generate(), "Curso", "", 25, Money.create(180000))).getType()); }
    @Test void rejectsZeroDuration() { assertThrows(DomainError.class, () -> Course.create(Id.generate(), "Curso", "Prof", 0, Money.create(180000))); }
    @Test void returnsPrimitives() {
        var p = Course.create(Id.create("c-1"), "Intro Java", "María", 40, Money.create(150000)).toPrimitives();
        assertEquals("c-1", p.get("id")); assertEquals("Intro Java", p.get("name"));
    }
}
