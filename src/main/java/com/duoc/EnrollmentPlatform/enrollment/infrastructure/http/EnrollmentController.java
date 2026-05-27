package com.duoc.enrollmentplatform.enrollment.infrastructure.http;

import com.duoc.enrollmentplatform.enrollment.application.CreateEnrollmentUseCase;
import com.duoc.enrollmentplatform.enrollment.application.DeleteEnrollmentUseCase;
import com.duoc.enrollmentplatform.enrollment.application.EnrollmentSummaryDTO;
import com.duoc.enrollmentplatform.enrollment.application.GetEnrollmentUseCase;
import com.duoc.enrollmentplatform.enrollment.application.ListEnrollmentsUseCase;
import com.duoc.enrollmentplatform.enrollment.application.UpdateEnrollmentUseCase;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final CreateEnrollmentUseCase createEnrollmentUseCase;
    private final ListEnrollmentsUseCase listEnrollmentsUseCase;
    private final GetEnrollmentUseCase getEnrollmentUseCase;
    private final UpdateEnrollmentUseCase updateEnrollmentUseCase;
    private final DeleteEnrollmentUseCase deleteEnrollmentUseCase;

    public EnrollmentController(CreateEnrollmentUseCase createEnrollmentUseCase,
                                ListEnrollmentsUseCase listEnrollmentsUseCase,
                                GetEnrollmentUseCase getEnrollmentUseCase,
                                UpdateEnrollmentUseCase updateEnrollmentUseCase,
                                DeleteEnrollmentUseCase deleteEnrollmentUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
        this.listEnrollmentsUseCase = listEnrollmentsUseCase;
        this.getEnrollmentUseCase = getEnrollmentUseCase;
        this.updateEnrollmentUseCase = updateEnrollmentUseCase;
        this.deleteEnrollmentUseCase = deleteEnrollmentUseCase;
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

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(listEnrollmentsUseCase.execute());
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<?> get(@PathVariable String enrollmentId) {
        try {
            return ResponseEntity.ok(getEnrollmentUseCase.execute(enrollmentId));
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @PutMapping("/{enrollmentId}")
    public ResponseEntity<?> update(@PathVariable String enrollmentId, @RequestBody Map<String, Object> body) {
        if (!(body.get("courseIds") instanceof List<?> rawList))
            return ResponseEntity.badRequest().body(Map.of("error", "courseIds is required and must be a list"));

        List<String> courseIds = rawList.stream()
                .filter(item -> item instanceof String).map(item -> (String) item).toList();
        if (courseIds.size() != rawList.size())
            return ResponseEntity.badRequest().body(Map.of("error", "courseIds must contain valid string values"));

        try {
            return ResponseEntity.ok(updateEnrollmentUseCase.execute(enrollmentId, courseIds));
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<?> delete(@PathVariable String enrollmentId) {
        try {
            deleteEnrollmentUseCase.execute(enrollmentId);
            return ResponseEntity.noContent().build();
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
