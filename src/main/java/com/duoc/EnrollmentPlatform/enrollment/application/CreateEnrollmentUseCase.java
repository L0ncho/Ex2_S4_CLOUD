package com.duoc.enrollmentplatform.enrollment.application;

import com.duoc.enrollmentplatform.courses.domain.entities.Course;
import com.duoc.enrollmentplatform.courses.domain.repositories.CourseRepository;
import com.duoc.enrollmentplatform.enrollment.domain.entities.Enrollment;
import com.duoc.enrollmentplatform.enrollment.domain.entities.EnrollmentLine;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.StudentRepository;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import com.duoc.enrollmentplatform.shared.domain.valueobjects.Id;

import java.util.List;

public class CreateEnrollmentUseCase {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CreateEnrollmentUseCase(CourseRepository courseRepository,
                                   StudentRepository studentRepository,
                                   EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public EnrollmentSummaryDTO execute(String studentId, List<String> courseIds) {
        studentRepository.findById(Id.create(studentId))
                .orElseThrow(() -> DomainError.notFound("Student " + studentId + " not found"));

        List<EnrollmentLine> lines = courseIds.stream()
                .map(courseId -> {
                    Course course = courseRepository.findById(Id.create(courseId))
                            .orElseThrow(() -> DomainError.notFound("Course " + courseId + " not found"));
                    return EnrollmentLine.create(Id.generate(), course.getId(), course.getName(), course.getPrice());
                })
                .toList();

        Enrollment enrollment = Enrollment.create(Id.generate(), Id.create(studentId), lines);
        enrollmentRepository.save(enrollment);

        return toDTO(enrollment);
    }

    private EnrollmentSummaryDTO toDTO(Enrollment enrollment) {
        List<EnrollmentLineDTO> lineDTOs = enrollment.getLines().stream()
                .map(l -> new EnrollmentLineDTO(l.getCourseId().getValue(), l.getCourseName(), l.getUnitPrice().getValue()))
                .toList();
        return new EnrollmentSummaryDTO(enrollment.getId().getValue(), enrollment.getStudentId().getValue(),
                lineDTOs, enrollment.calculateTotal().getValue());
    }
}
