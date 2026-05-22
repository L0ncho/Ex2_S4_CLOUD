package com.duoc.enrollmentplatform.shared.tests.unit;

import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TheIdTest {
    @Test void createsFromValidString() { assertEquals("abc-123", Id.create("abc-123").getValue()); }
    @Test void generatesRandomUniqueValues() { assertNotEquals(Id.generate(), Id.generate()); }
    @Test void rejectsEmptyValue() { assertEquals(DomainError.Type.VALIDATION, assertThrows(DomainError.class, () -> Id.create("")).getType()); }
    @Test void rejectsNullValue() { assertThrows(DomainError.class, () -> Id.create(null)); }
    @Test void considersEqualWhenSameValue() { assertEquals(Id.create("same"), Id.create("same")); }
    @Test void exposesValueAsPrimitives() { assertEquals("test-id", Id.create("test-id").toPrimitives()); }
}
