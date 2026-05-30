package com.duoc.enrollmentplatform.shared.domain.valueobjects;

import com.duoc.enrollmentplatform.shared.domain.DomainError;

public final class Money {
    private final double amount;
    private Money(double amount) { this.amount = amount; }

    public static Money create(double amount) {
        if (amount < 0) throw DomainError.validation("Amount cannot be negative");
        return new Money(amount);
    }
    public static Money zero() { return new Money(0); }
    public Money add(Money other) { return new Money(this.amount + other.amount); }
    public double getValue() { return amount; }
    public double toPrimitives() { return amount; }

    @Override public boolean equals(Object o) { return o instanceof Money m && Double.compare(amount, m.amount) == 0; }
    @Override public int hashCode() { return Double.hashCode(amount); }
}
