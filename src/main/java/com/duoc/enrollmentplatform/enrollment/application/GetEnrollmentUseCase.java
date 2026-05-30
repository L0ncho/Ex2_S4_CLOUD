package com.duoc.enrollmentplatform.enrollment.application;

import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

public class GetEnrollmentUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentDtoMapper mapper;

    public GetEnrollmentUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.mapper = new EnrollmentDtoMapper();
    }

    public EnrollmentSummaryDTO execute(String enrollmentId) {
        return enrollmentRepository.findById(Id.create(enrollmentId))
                .map(mapper::toSummaryDto)
                .orElseThrow(() -> DomainError.notFound("Enrollment " + enrollmentId + " not found"));
    }
}
