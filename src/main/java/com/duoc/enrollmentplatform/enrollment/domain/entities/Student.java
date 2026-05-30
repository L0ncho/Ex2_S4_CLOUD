package com.duoc.enrollmentplatform.enrollment.domain.entities;

import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Email;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final Id id;
    private final String fullName;
    private final Email email;

    private Student(Id id, String fullName, Email email) {
        this.id = id; this.fullName = fullName; this.email = email;
    }

    public static Student create(Id id, String fullName, Email email) {
        if (fullName == null || fullName.isBlank()) throw DomainError.validation("Student full name is required");
        return new Student(id, fullName, email);
    }

    public Id getId() { return id; }
    public String getFullName() { return fullName; }
    public Email getEmail() { return email; }

    public Map<String, Object> toPrimitives() {
        Map<String, Object> p = new HashMap<>();
        p.put("id", id.getValue()); p.put("fullName", fullName); p.put("email", email.getValue());
        return p;
    }
}
