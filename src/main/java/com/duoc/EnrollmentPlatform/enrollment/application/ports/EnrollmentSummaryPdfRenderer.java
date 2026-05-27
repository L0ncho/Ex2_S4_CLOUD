package com.duoc.enrollmentplatform.enrollment.application.ports;

public interface EnrollmentSummaryPdfRenderer {
    byte[] render(byte[] summaryJson);
}
