package com.duoc.enrollmentplatform.enrollment.application.summary;

import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;

import java.util.List;

public class ListEnrollmentSummariesUseCase {
    private final EnrollmentSummaryStorage summaryStorage;

    public ListEnrollmentSummariesUseCase(EnrollmentSummaryStorage summaryStorage) {
        this.summaryStorage = summaryStorage;
    }

    public List<SummaryObjectInfo> execute() {
        return summaryStorage.listAll();
    }
}
