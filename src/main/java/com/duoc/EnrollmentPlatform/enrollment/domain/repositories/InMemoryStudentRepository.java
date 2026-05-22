package com.duoc.enrollmentplatform.enrollment.domain.repositories;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Student;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

import java.util.*;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<String, Student> students = new HashMap<>();

    public InMemoryStudentRepository() {}
    public InMemoryStudentRepository(List<Student> initial) {
        initial.forEach(s -> students.put(s.getId().getValue(), s));
    }

    @Override public Optional<Student> findById(Id id) { return Optional.ofNullable(students.get(id.getValue())); }
    @Override public void save(Student student) { students.put(student.getId().getValue(), student); }
}
