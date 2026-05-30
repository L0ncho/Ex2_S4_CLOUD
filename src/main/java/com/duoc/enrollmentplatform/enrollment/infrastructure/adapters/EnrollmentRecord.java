package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enrollments")
class EnrollmentRecord {
    @Id @Column(name = "id") String id;
    @Column(name = "student_id", nullable = false) String studentId;
    @Column(name = "enrolled_at", nullable = false) LocalDateTime enrolledAt;
    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2) BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "enrollment_id", nullable = false)
    List<EnrollmentLineRecord> lines = new ArrayList<>();

    protected EnrollmentRecord() {}
    EnrollmentRecord(String id, String studentId, LocalDateTime enrolledAt,
                     BigDecimal totalAmount, List<EnrollmentLineRecord> lines) {
        this.id = id; this.studentId = studentId; this.enrolledAt = enrolledAt;
        this.totalAmount = totalAmount; this.lines = lines;
    }
}
