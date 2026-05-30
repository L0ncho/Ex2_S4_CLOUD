package com.duoc.enrollmentplatform.shared.domain.valueobjects;

import com.duoc.enrollmentplatform.shared.domain.DomainError;
import java.util.UUID;

public final class Id {
    private final String value;
    private Id(String value) { this.value = value; }

    public static Id create(String value) {
        if (value == null || value.isBlank()) throw DomainError.validation("Id cannot be empty");
        return new Id(value);
    }
    public static Id generate() { return new Id(UUID.randomUUID().toString()); }
    public String getValue() { return value; }
    public String toPrimitives() { return value; }

    @Override public boolean equals(Object o) { return o instanceof Id id && value.equals(id.value); }
    @Override public int hashCode() { return value.hashCode(); }
}
