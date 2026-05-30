package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "enrollment_lines")
class EnrollmentLineRecord {
    @Id @Column(name = "id") String id;
    @Column(name = "course_id", nullable = false) String courseId;
    @Column(name = "course_name", nullable = false) String courseName;
    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2) BigDecimal unitPrice;

    protected EnrollmentLineRecord() {}
    EnrollmentLineRecord(String id, String courseId, String courseName, BigDecimal unitPrice) {
        this.id = id; this.courseId = courseId; this.courseName = courseName; this.unitPrice = unitPrice;
    }
}
