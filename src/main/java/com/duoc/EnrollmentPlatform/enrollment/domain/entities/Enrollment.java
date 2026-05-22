package com.duoc.enrollmentplatform.enrollment.domain.entities;

import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Enrollment {
    private final Id id;
    private final Id studentId;
    private final List<EnrollmentLine> lines;
    private final LocalDateTime enrolledAt;

    private Enrollment(Id id, Id studentId, List<EnrollmentLine> lines, LocalDateTime enrolledAt) {
        this.id = id; this.studentId = studentId;
        this.lines = List.copyOf(lines); this.enrolledAt = enrolledAt;
    }

    public static Enrollment create(Id id, Id studentId, List<EnrollmentLine> lines) {
        if (lines == null || lines.isEmpty()) throw DomainError.validation("Enrollment must include at least one course");
        return new Enrollment(id, studentId, lines, LocalDateTime.now());
    }

    public static Enrollment reconstitute(Id id, Id studentId, List<EnrollmentLine> lines, LocalDateTime enrolledAt) {
        return new Enrollment(id, studentId, lines, enrolledAt);
    }

    public Money calculateTotal() {
        return lines.stream().map(EnrollmentLine::getUnitPrice).reduce(Money.zero(), Money::add);
    }

    public Id getId() { return id; }
    public Id getStudentId() { return studentId; }
    public List<EnrollmentLine> getLines() { return new ArrayList<>(lines); }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    public Map<String, Object> toPrimitives() {
        Map<String, Object> p = new HashMap<>();
        p.put("id", id.getValue()); p.put("studentId", studentId.getValue());
        p.put("enrolledAt", enrolledAt.toString()); p.put("totalAmount", calculateTotal().getValue());
        p.put("lines", lines.stream().map(EnrollmentLine::toPrimitives).toList());
        return p;
    }
}
