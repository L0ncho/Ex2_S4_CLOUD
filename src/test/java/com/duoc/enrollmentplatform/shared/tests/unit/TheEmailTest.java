package com.duoc.enrollmentplatform.shared.tests.unit;

import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TheEmailTest {
    @Test void createsFromValidAddress() { assertEquals("juan.soto@duoc.cl", Email.create("juan.soto@duoc.cl").getValue()); }
    @Test void rejectsAddressWithoutAtSign() { assertEquals(DomainError.Type.VALIDATION, assertThrows(DomainError.class, () -> Email.create("invalidemail")).getType()); }
    @Test void rejectsNullAddress() { assertThrows(DomainError.class, () -> Email.create(null)); }
    @Test void rejectsBlankAddress() { assertThrows(DomainError.class, () -> Email.create("   ")); }
    @Test void considersEqualWhenSameAddress() { assertEquals(Email.create("juan@duoc.cl"), Email.create("juan@duoc.cl")); }
}
