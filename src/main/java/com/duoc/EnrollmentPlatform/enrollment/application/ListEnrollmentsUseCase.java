package com.duoc.enrollmentplatform.enrollment.application;

import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;

import java.util.List;

public class ListEnrollmentsUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentDtoMapper mapper;

    public ListEnrollmentsUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.mapper = new EnrollmentDtoMapper();
    }

    public List<EnrollmentSummaryDTO> execute() {
        return enrollmentRepository.findAll().stream()
                .map(mapper::toSummaryDto)
                .toList();
    }
}
