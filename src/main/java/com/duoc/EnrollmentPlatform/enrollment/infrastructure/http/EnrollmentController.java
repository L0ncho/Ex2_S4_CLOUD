package com.duoc.enrollmentplatform.enrollment.infrastructure.http;

import com.duoc.enrollmentplatform.enrollment.application.CreateEnrollmentUseCase;
import com.duoc.enrollmentplatform.enrollment.application.EnrollmentSummaryDTO;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final CreateEnrollmentUseCase createEnrollmentUseCase;

    public EnrollmentController(CreateEnrollmentUseCase createEnrollmentUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        if (!(body.get("studentId") instanceof String studentId))
            return ResponseEntity.badRequest().body(Map.of("error", "studentId is required and must be a string"));
        if (!(body.get("courseIds") instanceof List<?> rawList))
            return ResponseEntity.badRequest().body(Map.of("error", "courseIds is required and must be a list"));

        List<String> courseIds = rawList.stream()
                .filter(item -> item instanceof String).map(item -> (String) item).toList();
        if (courseIds.size() != rawList.size())
            return ResponseEntity.badRequest().body(Map.of("error", "courseIds must contain valid string values"));

        try {
            EnrollmentSummaryDTO dto = createEnrollmentUseCase.execute(studentId, courseIds);
            return ResponseEntity.status(201).body(dto);
        } catch (Exception error) {
            return handleError(error);
        }
    }

    private ResponseEntity<?> handleError(Exception error) {
        if (error instanceof DomainError domainError) {
            int status = switch (domainError.getType()) {
                case NOT_FOUND -> 404; case VALIDATION -> 422; default -> 400;
            };
            return ResponseEntity.status(status).body(Map.of("error", domainError.getMessage()));
        }
        return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
    }
}
