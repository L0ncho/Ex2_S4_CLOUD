package com.duoc.enrollmentplatform.enrollment.application;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Enrollment;

import java.util.List;

public class EnrollmentDtoMapper {
    public EnrollmentSummaryDTO toSummaryDto(Enrollment enrollment) {
        List<EnrollmentLineDTO> lineDtos = enrollment.getLines().stream()
                .map(line -> new EnrollmentLineDTO(
                        line.getCourseId().getValue(),
                        line.getCourseName(),
                        line.getUnitPrice().getValue()))
                .toList();
        return new EnrollmentSummaryDTO(
                enrollment.getId().getValue(),
                enrollment.getStudentId().getValue(),
                lineDtos,
                enrollment.calculateTotal().getValue());
    }
}
