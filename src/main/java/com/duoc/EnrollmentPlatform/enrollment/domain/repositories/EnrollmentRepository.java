package com.duoc.enrollmentplatform.enrollment.domain.repositories;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Enrollment;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import java.util.Optional;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    Optional<Enrollment> findById(Id id);
}
