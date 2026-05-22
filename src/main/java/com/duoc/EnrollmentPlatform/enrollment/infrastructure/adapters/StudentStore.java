package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentStore extends JpaRepository<StudentRecord, String> {}
