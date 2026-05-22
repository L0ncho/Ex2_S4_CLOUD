package com.duoc.enrollmentplatform.factory;

import com.duoc.enrollmentplatform.courses.domain.repositories.CourseRepository;
import com.duoc.enrollmentplatform.courses.infrastructure.adapters.CourseStore;
import com.duoc.enrollmentplatform.courses.infrastructure.http.CourseController;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.EnrollmentRepository;
import com.duoc.enrollmentplatform.enrollment.domain.repositories.StudentRepository;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.EnrollmentStore;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.StudentStore;
import com.duoc.enrollmentplatform.enrollment.infrastructure.http.EnrollmentController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CourseRepository courseRepository(CourseStore store) {
        return EnrollmentPlatformFactory.getCourseRepository(store);
    }

    @Bean
    public StudentRepository studentRepository(StudentStore store) {
        return EnrollmentPlatformFactory.getStudentRepository(store);
    }

    @Bean
    public EnrollmentRepository enrollmentRepository(EnrollmentStore store) {
        return EnrollmentPlatformFactory.getEnrollmentRepository(store);
    }

    @Bean
    public CourseController courseController(CourseRepository courseRepository) {
        return EnrollmentPlatformFactory.createCourseController(courseRepository);
    }

    @Bean
    public EnrollmentController enrollmentController(
            CourseRepository courseRepository,
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository) {
        return EnrollmentPlatformFactory.createEnrollmentController(courseRepository, studentRepository, enrollmentRepository);
    }
}
