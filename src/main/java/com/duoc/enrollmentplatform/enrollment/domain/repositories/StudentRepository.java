package com.duoc.enrollmentplatform.enrollment.domain.repositories;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Student;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(Id id);
    void save(Student student);
}
