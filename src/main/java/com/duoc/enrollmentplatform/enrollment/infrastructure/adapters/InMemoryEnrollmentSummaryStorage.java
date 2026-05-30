package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import com.duoc.enrollmentplatform.enrollment.application.summary.StoredSummary;
import com.duoc.enrollmentplatform.enrollment.application.summary.SummaryObjectInfo;
import com.duoc.enrollmentplatform.enrollment.application.summary.SummaryUploadResult;
import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryEnrollmentSummaryStorage implements EnrollmentSummaryStorage {
    private static final String CONTENT_TYPE = "application/json";
    private static final String BUCKET = "in-memory";

    private final Map<String, byte[]> objects = new HashMap<>();
    private final Map<String, Instant> lastModified = new HashMap<>();

    @Override
    public SummaryUploadResult upload(String enrollmentId, byte[] content) {
        return store(enrollmentId, content);
    }

    @Override
    public SummaryUploadResult replace(String enrollmentId, byte[] content) {
        return store(enrollmentId, content);
    }

    @Override
    public Optional<StoredSummary> download(String enrollmentId) {
        byte[] content = objects.get(enrollmentId);
        if (content == null) {
            return Optional.empty();
        }
        return Optional.of(new StoredSummary(content, CONTENT_TYPE));
    }

    @Override
    public void delete(String enrollmentId) {
        objects.remove(enrollmentId);
        lastModified.remove(enrollmentId);
    }

    @Override
    public boolean exists(String enrollmentId) {
        return objects.containsKey(enrollmentId);
    }

    @Override
    public List<SummaryObjectInfo> listAll() {
        List<SummaryObjectInfo> result = new ArrayList<>();
        for (Map.Entry<String, byte[]> entry : objects.entrySet()) {
            String enrollmentId = entry.getKey();
            String key = enrollmentId + "/summary.json";
            result.add(new SummaryObjectInfo(
                    enrollmentId,
                    key,
                    entry.getValue().length,
                    lastModified.get(enrollmentId)));
        }
        return result;
    }

    private SummaryUploadResult store(String enrollmentId, byte[] content) {
        objects.put(enrollmentId, content);
        lastModified.put(enrollmentId, Instant.now());
        String key = enrollmentId + "/summary.json";
        return new SummaryUploadResult(enrollmentId, key, BUCKET);
    }
}
