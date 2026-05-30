package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Student;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.StudentRepository;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Email;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

import java.util.Optional;

public class JpaStudentRepository implements StudentRepository {
    private final StudentStore store;
    public JpaStudentRepository(StudentStore store) { this.store = store; }

    @Override
    public Optional<Student> findById(Id id) { return store.findById(id.getValue()).map(this::toDomain); }

    @Override
    public void save(Student student) {
        store.save(new StudentRecord(student.getId().getValue(), student.getFullName(), student.getEmail().getValue()));
    }

    private Student toDomain(StudentRecord r) {
        return Student.create(Id.create(r.id), r.fullName, Email.create(r.email));
    }
}
