package com.duoc.enrollmentplatform.enrollment.application.summary;

import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReplaceEnrollmentSummaryUseCase {
    private final EnrollmentSummaryStorage summaryStorage;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ReplaceEnrollmentSummaryUseCase(EnrollmentSummaryStorage summaryStorage) {
        this.summaryStorage = summaryStorage;
    }

    public SummaryUploadResult execute(String enrollmentId, byte[] content) {
        validateJson(enrollmentId, content);
        if (!summaryStorage.exists(enrollmentId)) {
            throw DomainError.notFound("Summary for enrollment " + enrollmentId + " not found");
        }
        return summaryStorage.replace(enrollmentId, content);
    }

    private void validateJson(String enrollmentId, byte[] content) {
        try {
            JsonNode root = objectMapper.readTree(content);
            if (!root.hasNonNull("enrollmentId")) {
                throw DomainError.validation("Summary must include enrollmentId");
            }
            if (!enrollmentId.equals(root.get("enrollmentId").asText())) {
                throw DomainError.validation("Summary enrollmentId must match path");
            }
        } catch (DomainError error) {
            throw error;
        } catch (Exception error) {
            throw DomainError.validation("Summary must be valid JSON");
        }
    }
}
