package com.duoc.enrollmentplatform.courses.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStore extends JpaRepository<CourseRecord, String> {}
