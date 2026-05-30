package com.duoc.enrollmentplatform.enrollment.infrastructure.http;

import com.duoc.enrollmentplatform.enrollment.application.summary.DeleteEnrollmentSummaryUseCase;
import com.duoc.enrollmentplatform.enrollment.application.summary.DownloadEnrollmentSummaryUseCase;
import com.duoc.enrollmentplatform.enrollment.application.summary.GenerateEnrollmentSummaryFileUseCase;
import com.duoc.enrollmentplatform.enrollment.application.summary.GeneratedSummaryFile;
import com.duoc.enrollmentplatform.enrollment.application.summary.ListEnrollmentSummariesUseCase;
import com.duoc.enrollmentplatform.enrollment.application.summary.ReplaceEnrollmentSummaryUseCase;
import com.duoc.enrollmentplatform.enrollment.application.summary.SummaryUploadResult;
import com.duoc.enrollmentplatform.enrollment.application.summary.UploadEnrollmentSummaryUseCase;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentSummaryController {
    private final GenerateEnrollmentSummaryFileUseCase generateEnrollmentSummaryFileUseCase;
    private final UploadEnrollmentSummaryUseCase uploadEnrollmentSummaryUseCase;
    private final DownloadEnrollmentSummaryUseCase downloadEnrollmentSummaryUseCase;
    private final ReplaceEnrollmentSummaryUseCase replaceEnrollmentSummaryUseCase;
    private final DeleteEnrollmentSummaryUseCase deleteEnrollmentSummaryUseCase;
    private final ListEnrollmentSummariesUseCase listEnrollmentSummariesUseCase;

    public EnrollmentSummaryController(
            GenerateEnrollmentSummaryFileUseCase generateEnrollmentSummaryFileUseCase,
            UploadEnrollmentSummaryUseCase uploadEnrollmentSummaryUseCase,
            DownloadEnrollmentSummaryUseCase downloadEnrollmentSummaryUseCase,
            ReplaceEnrollmentSummaryUseCase replaceEnrollmentSummaryUseCase,
            DeleteEnrollmentSummaryUseCase deleteEnrollmentSummaryUseCase,
            ListEnrollmentSummariesUseCase listEnrollmentSummariesUseCase) {
        this.generateEnrollmentSummaryFileUseCase = generateEnrollmentSummaryFileUseCase;
        this.uploadEnrollmentSummaryUseCase = uploadEnrollmentSummaryUseCase;
        this.downloadEnrollmentSummaryUseCase = downloadEnrollmentSummaryUseCase;
        this.replaceEnrollmentSummaryUseCase = replaceEnrollmentSummaryUseCase;
        this.deleteEnrollmentSummaryUseCase = deleteEnrollmentSummaryUseCase;
        this.listEnrollmentSummariesUseCase = listEnrollmentSummariesUseCase;
    }

    @GetMapping("/summaries")
    public ResponseEntity<?> listSummaries() {
        try {
            return ResponseEntity.ok(listEnrollmentSummariesUseCase.execute());
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @GetMapping("/{enrollmentId}/summary/file")
    public ResponseEntity<?> generateFile(@PathVariable String enrollmentId) {
        try {
            GeneratedSummaryFile file = generateEnrollmentSummaryFileUseCase.execute(enrollmentId);
            return attachmentResponse(file);
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @PostMapping("/{enrollmentId}/summary")
    public ResponseEntity<?> upload(@PathVariable String enrollmentId) {
        try {
            SummaryUploadResult result = uploadEnrollmentSummaryUseCase.execute(enrollmentId);
            return ResponseEntity.status(201).body(Map.of(
                    "enrollmentId", result.enrollmentId,
                    "s3Key", result.s3Key,
                    "bucket", result.bucket));
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @GetMapping("/{enrollmentId}/summary")
    public ResponseEntity<?> download(@PathVariable String enrollmentId,
                                      @RequestParam(defaultValue = "json") String format) {
        try {
            GeneratedSummaryFile file = downloadEnrollmentSummaryUseCase.execute(enrollmentId, format);
            return attachmentResponse(file);
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @PutMapping("/{enrollmentId}/summary")
    public ResponseEntity<?> replace(@PathVariable String enrollmentId, @RequestBody byte[] body) {
        try {
            SummaryUploadResult result = replaceEnrollmentSummaryUseCase.execute(enrollmentId, body);
            return ResponseEntity.ok(Map.of(
                    "enrollmentId", result.enrollmentId,
                    "s3Key", result.s3Key,
                    "bucket", result.bucket));
        } catch (Exception error) {
            return handleError(error);
        }
    }

    @DeleteMapping("/{enrollmentId}/summary")
    public ResponseEntity<?> delete(@PathVariable String enrollmentId) {
        try {
            deleteEnrollmentSummaryUseCase.execute(enrollmentId);
            return ResponseEntity.noContent().build();
        } catch (Exception error) {
            return handleError(error);
        }
    }

    private ResponseEntity<byte[]> attachmentResponse(GeneratedSummaryFile file) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.filename + "\"")
                .contentType(MediaType.parseMediaType(file.contentType))
                .body(file.content);
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
