package com.duoc.enrollmentplatform.enrollment.tests.unit;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Enrollment;
import com.duoc.enrollmentplatform.enrollment.domain.entities.EnrollmentLine;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.InMemoryEnrollmentRepository;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryEnrollmentRepositoryTest {

    @Test
    void deletesEnrollmentById() {
        InMemoryEnrollmentRepository repository = new InMemoryEnrollmentRepository();
        Enrollment enrollment = Enrollment.create(Id.generate(), Id.generate(), List.of(
                EnrollmentLine.create(Id.generate(), Id.generate(), "Intro Java", Money.create(150000))));
        repository.save(enrollment);
        repository.deleteById(enrollment.getId());
        assertTrue(repository.findById(enrollment.getId()).isEmpty());
    }

    @Test
    void listsAllEnrollments() {
        InMemoryEnrollmentRepository repository = new InMemoryEnrollmentRepository();
        Enrollment first = Enrollment.create(Id.generate(), Id.generate(), List.of(
                EnrollmentLine.create(Id.generate(), Id.generate(), "A", Money.create(100))));
        Enrollment second = Enrollment.create(Id.generate(), Id.generate(), List.of(
                EnrollmentLine.create(Id.generate(), Id.generate(), "B", Money.create(200))));
        repository.save(first);
        repository.save(second);
        assertEquals(2, repository.findAll().size());
    }
}
