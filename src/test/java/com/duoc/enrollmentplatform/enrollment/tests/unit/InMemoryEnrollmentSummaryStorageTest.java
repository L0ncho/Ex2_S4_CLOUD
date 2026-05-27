package com.duoc.enrollmentplatform.enrollment.tests.unit;

import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.InMemoryEnrollmentSummaryStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryEnrollmentSummaryStorageTest {

    @Test
    void uploadsAndDownloadsSummary() {
        InMemoryEnrollmentSummaryStorage storage = new InMemoryEnrollmentSummaryStorage();
        byte[] content = "{\"enrollmentId\":\"e-1\"}".getBytes();

        storage.upload("e-1", content);

        assertTrue(storage.exists("e-1"));
        assertArrayEquals(content, storage.download("e-1").orElseThrow().content);
    }

    @Test
    void replacesExistingSummary() {
        InMemoryEnrollmentSummaryStorage storage = new InMemoryEnrollmentSummaryStorage();
        storage.upload("e-1", "v1".getBytes());

        storage.replace("e-1", "v2".getBytes());

        assertEquals("v2", new String(storage.download("e-1").orElseThrow().content));
    }

    @Test
    void listsStoredSummaries() {
        InMemoryEnrollmentSummaryStorage storage = new InMemoryEnrollmentSummaryStorage();
        storage.upload("e-1", "{}".getBytes());
        storage.upload("e-2", "{}".getBytes());

        assertEquals(2, storage.listAll().size());
    }
}
