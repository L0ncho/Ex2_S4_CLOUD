package com.duoc.enrollmentplatform.courses.infrastructure.adapters;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "courses")
class CourseRecord {
    @Id @Column(name = "id") String id;
    @Column(name = "name", nullable = false) String name;
    @Column(name = "instructor", nullable = false) String instructor;
    @Column(name = "duration_hours", nullable = false) int durationHours;
    @Column(name = "price", nullable = false, precision = 15, scale = 2) BigDecimal price;

    protected CourseRecord() {}
    CourseRecord(String id, String name, String instructor, int durationHours, BigDecimal price) {
        this.id = id; this.name = name; this.instructor = instructor;
        this.durationHours = durationHours; this.price = price;
    }
}
