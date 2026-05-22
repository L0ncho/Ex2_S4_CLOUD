package com.duoc.enrollmentplatform.enrollment.domain.entities;

import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;

import java.util.HashMap;
import java.util.Map;

public class EnrollmentLine {
    private final Id id;
    private final Id courseId;
    private final String courseName;
    private final Money unitPrice;

    private EnrollmentLine(Id id, Id courseId, String courseName, Money unitPrice) {
        this.id = id; this.courseId = courseId; this.courseName = courseName; this.unitPrice = unitPrice;
    }

    public static EnrollmentLine create(Id id, Id courseId, String courseName, Money unitPrice) {
        return new EnrollmentLine(id, courseId, courseName, unitPrice);
    }

    public Id getId() { return id; }
    public Id getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public Money getUnitPrice() { return unitPrice; }

    public Map<String, Object> toPrimitives() {
        Map<String, Object> p = new HashMap<>();
        p.put("id", id.getValue()); p.put("courseId", courseId.getValue());
        p.put("courseName", courseName); p.put("unitPrice", unitPrice.getValue());
        return p;
    }
}
