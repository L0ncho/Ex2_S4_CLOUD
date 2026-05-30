package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentStore extends JpaRepository<EnrollmentRecord, String> {}
