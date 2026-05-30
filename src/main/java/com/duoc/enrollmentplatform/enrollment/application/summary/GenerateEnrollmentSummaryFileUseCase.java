package com.duoc.enrollmentplatform.enrollment.application.summary;

import com.duoc.enrollmentplatform.enrollment.domain.entities.Enrollment;
import com.duoc.enrollmentplatform.enrollment.domain.entities.Student;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.StudentRepository;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

public class GenerateEnrollmentSummaryFileUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentSummaryGenerator summaryGenerator;

    public GenerateEnrollmentSummaryFileUseCase(EnrollmentRepository enrollmentRepository,
                                                StudentRepository studentRepository,
                                                EnrollmentSummaryGenerator summaryGenerator) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.summaryGenerator = summaryGenerator;
    }

    public GeneratedSummaryFile execute(String enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(Id.create(enrollmentId))
                .orElseThrow(() -> DomainError.notFound("Enrollment " + enrollmentId + " not found"));
        Student student = studentRepository.findById(enrollment.getStudentId())
                .orElseThrow(() -> DomainError.notFound("Student " + enrollment.getStudentId().getValue() + " not found"));
        byte[] content = summaryGenerator.toJsonBytes(enrollment, student);
        String filename = "summary-" + enrollmentId + ".json";
        return new GeneratedSummaryFile(content, filename, "application/json");
    }
}
