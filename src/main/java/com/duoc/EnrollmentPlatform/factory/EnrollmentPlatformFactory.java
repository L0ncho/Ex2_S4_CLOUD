package com.duoc.enrollmentplatform.factory;

import com.duoc.enrollmentplatform.courses.application.CreateCourseUseCase;
import com.duoc.enrollmentplatform.courses.application.ListCoursesUseCase;
import com.duoc.enrollmentplatform.courses.domain.repositories.CourseRepository;
import com.duoc.enrollmentplatform.courses.infrastructure.adapters.CourseStore;
import com.duoc.enrollmentplatform.courses.infrastructure.adapters.JpaCourseRepository;
import com.duoc.enrollmentplatform.courses.infrastructure.http.CourseController;
import com.duoc.enrollmentplatform.enrollment.application.CreateEnrollmentUseCase;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.StudentRepository;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.EnrollmentStore;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.JpaEnrollmentRepository;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.JpaStudentRepository;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.StudentStore;
import com.duoc.enrollmentplatform.enrollment.infrastructure.http.EnrollmentController;

public class EnrollmentPlatformFactory {

    public static CourseRepository getCourseRepository(CourseStore store) {
        return new JpaCourseRepository(store);
    }

    public static StudentRepository getStudentRepository(StudentStore store) {
        return new JpaStudentRepository(store);
    }

    public static EnrollmentRepository getEnrollmentRepository(EnrollmentStore store) {
        return new JpaEnrollmentRepository(store);
    }

    public static CourseController createCourseController(CourseRepository courseRepository) {
        return new CourseController(
                new ListCoursesUseCase(courseRepository),
                new CreateCourseUseCase(courseRepository)
        );
    }

    public static EnrollmentController createEnrollmentController(
            CourseRepository courseRepository,
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository) {
        return new EnrollmentController(
                new CreateEnrollmentUseCase(courseRepository, studentRepository, enrollmentRepository)
        );
    }
}
