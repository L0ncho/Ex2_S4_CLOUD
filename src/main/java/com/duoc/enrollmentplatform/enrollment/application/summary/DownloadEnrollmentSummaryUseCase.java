package com.duoc.enrollmentplatform.enrollment.application.summary;

import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryPdfRenderer;
import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;
import com.duoc.enrollmentplatform.shared.domain.DomainError;

public class DownloadEnrollmentSummaryUseCase {
    private final EnrollmentSummaryStorage summaryStorage;
    private final EnrollmentSummaryPdfRenderer pdfRenderer;

    public DownloadEnrollmentSummaryUseCase(EnrollmentSummaryStorage summaryStorage,
                                            EnrollmentSummaryPdfRenderer pdfRenderer) {
        this.summaryStorage = summaryStorage;
        this.pdfRenderer = pdfRenderer;
    }

    public GeneratedSummaryFile execute(String enrollmentId, String format) {
        StoredSummary stored = summaryStorage.download(enrollmentId)
                .orElseThrow(() -> DomainError.notFound("Summary for enrollment " + enrollmentId + " not found"));

        if ("pdf".equalsIgnoreCase(format)) {
            byte[] pdf = pdfRenderer.render(stored.content);
            return new GeneratedSummaryFile(pdf, "summary-" + enrollmentId + ".pdf", "application/pdf");
        }

        return new GeneratedSummaryFile(stored.content, "summary-" + enrollmentId + ".json", stored.contentType);
    }
}
