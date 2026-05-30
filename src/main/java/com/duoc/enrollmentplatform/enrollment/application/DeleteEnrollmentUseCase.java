package com.duoc.enrollmentplatform.enrollment.application;

import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;
import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

public class DeleteEnrollmentUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentSummaryStorage summaryStorage;

    public DeleteEnrollmentUseCase(EnrollmentRepository enrollmentRepository,
                                 EnrollmentSummaryStorage summaryStorage) {
        this.enrollmentRepository = enrollmentRepository;
        this.summaryStorage = summaryStorage;
    }

    public void execute(String enrollmentId) {
        Id id = Id.create(enrollmentId);
        if (enrollmentRepository.findById(id).isEmpty()) {
            throw DomainError.notFound("Enrollment " + enrollmentId + " not found");
        }
        enrollmentRepository.deleteById(id);
        if (summaryStorage.exists(enrollmentId)) {
            summaryStorage.delete(enrollmentId);
        }
    }
}
