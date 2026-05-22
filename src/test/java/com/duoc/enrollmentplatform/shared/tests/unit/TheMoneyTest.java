package com.duoc.enrollmentplatform.shared.tests.unit;

import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TheMoneyTest {
    @Test void createsWithPositiveAmount() { assertEquals(150000, Money.create(150000).getValue()); }
    @Test void createsWithZeroAmount() { assertEquals(0, Money.create(0).getValue()); }
    @Test void rejectsNegativeAmount() { assertEquals(DomainError.Type.VALIDATION, assertThrows(DomainError.class, () -> Money.create(-1)).getType()); }
    @Test void addsAmountsReturningNewInstance() {
        Money total = Money.create(100000).add(Money.create(50000));
        assertEquals(150000, total.getValue());
    }
    @Test void zeroIsNeutralForAddition() { assertEquals(Money.create(120000), Money.create(120000).add(Money.zero())); }
    @Test void considersEqualWhenSameAmount() { assertEquals(Money.create(180000), Money.create(180000)); }
}
