package com.duoc.enrollmentplatform.enrollment.tests.unit;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Student;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Email;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TheStudentTest {
    @Test void createsWithValidAttributes() {
        Student student = Student.create(Id.generate(), "Juan Soto", Email.create("juan.soto@duoc.cl"));
        assertNotNull(student); assertEquals("Juan Soto", student.getFullName());
    }
    @Test void rejectsEmptyFullName() {
        assertEquals(DomainError.Type.VALIDATION,
                assertThrows(DomainError.class, () -> Student.create(Id.generate(), "", Email.create("juan@duoc.cl"))).getType());
    }
    @Test void exposesPrimitives() {
        var p = Student.create(Id.create("s-1"), "Valentina Muñoz", Email.create("valentina@duoc.cl")).toPrimitives();
        assertEquals("s-1", p.get("id")); assertEquals("Valentina Muñoz", p.get("fullName"));
    }
}
