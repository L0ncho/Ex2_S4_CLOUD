package com.duoc.enrollmentplatform.enrollment.application.summary;

import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;
import com.duoc.enrollmentplatform.shared.domain.DomainError;

public class DeleteEnrollmentSummaryUseCase {
    private final EnrollmentSummaryStorage summaryStorage;

    public DeleteEnrollmentSummaryUseCase(EnrollmentSummaryStorage summaryStorage) {
        this.summaryStorage = summaryStorage;
    }

    public void execute(String enrollmentId) {
        if (!summaryStorage.exists(enrollmentId)) {
            throw DomainError.notFound("Summary for enrollment " + enrollmentId + " not found");
        }
        summaryStorage.delete(enrollmentId);
    }
}
