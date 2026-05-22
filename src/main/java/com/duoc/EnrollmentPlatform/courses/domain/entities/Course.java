package com.duoc.enrollmentplatform.courses.domain.entities;

import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private final Id id;
    private final String name;
    private final String instructor;
    private final int durationHours;
    private final Money price;

    private Course(Id id, String name, String instructor, int durationHours, Money price) {
        this.id = id; this.name = name; this.instructor = instructor;
        this.durationHours = durationHours; this.price = price;
    }

    public static Course create(Id id, String name, String instructor, int durationHours, Money price) {
        if (name == null || name.isBlank()) throw DomainError.validation("Course name is required");
        if (instructor == null || instructor.isBlank()) throw DomainError.validation("Instructor name is required");
        if (durationHours <= 0) throw DomainError.validation("Duration must be positive");
        return new Course(id, name, instructor, durationHours, price);
    }

    public Id getId() { return id; }
    public String getName() { return name; }
    public String getInstructor() { return instructor; }
    public int getDurationHours() { return durationHours; }
    public Money getPrice() { return price; }

    public Map<String, Object> toPrimitives() {
        Map<String, Object> p = new HashMap<>();
        p.put("id", id.getValue()); p.put("name", name);
        p.put("instructor", instructor); p.put("durationHours", durationHours);
        p.put("price", price.getValue());
        return p;
    }
}
