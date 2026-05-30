package com.duoc.enrollmentplatform.shared.domain.valueobjects;

import com.duoc.enrollmentplatform.shared.domain.DomainError;

public final class Email {
    private final String value;
    private Email(String value) { this.value = value; }

    public static Email create(String value) {
        if (value == null || value.isBlank() || !value.contains("@"))
            throw DomainError.validation("Invalid email format");
        return new Email(value);
    }
    public String getValue() { return value; }
    public String toPrimitives() { return value; }

    @Override public boolean equals(Object o) { return o instanceof Email e && value.equals(e.value); }
    @Override public int hashCode() { return value.hashCode(); }
}
