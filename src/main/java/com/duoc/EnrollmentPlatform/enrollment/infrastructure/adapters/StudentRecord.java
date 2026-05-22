package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
class StudentRecord {
    @Id @Column(name = "id") String id;
    @Column(name = "full_name", nullable = false) String fullName;
    @Column(name = "email", nullable = false, unique = true) String email;

    protected StudentRecord() {}
    StudentRecord(String id, String fullName, String email) {
        this.id = id; this.fullName = fullName; this.email = email;
    }
}
