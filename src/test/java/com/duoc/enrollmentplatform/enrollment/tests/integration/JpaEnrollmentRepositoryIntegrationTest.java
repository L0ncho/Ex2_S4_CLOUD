package com.duoc.enrollmentplatform.enrollment.tests.integration;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Enrollment;
import com.duoc.enrollmentplatform.enrollment.domain.entities.EnrollmentLine;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.EnrollmentStore;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.JpaEnrollmentRepository;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class JpaEnrollmentRepositoryIntegrationTest {

    @Autowired
    private EnrollmentStore enrollmentStore;

    @Test
    void deletesEnrollmentById() {
        EnrollmentRepository repository = new JpaEnrollmentRepository(enrollmentStore);
        Enrollment enrollment = Enrollment.create(Id.generate(), Id.create("s-001"), List.of(
                EnrollmentLine.create(Id.generate(), Id.create("c-001"), "Introducción a Java", Money.create(150000))));
        repository.save(enrollment);

        repository.deleteById(enrollment.getId());

        assertTrue(repository.findById(enrollment.getId()).isEmpty());
    }
}
