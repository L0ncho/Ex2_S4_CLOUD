package com.duoc.enrollmentplatform.enrollment.application.ports;

import com.duoc.enrollmentplatform.enrollment.application.summary.SummaryObjectInfo;
import com.duoc.enrollmentplatform.enrollment.application.summary.StoredSummary;
import com.duoc.enrollmentplatform.enrollment.application.summary.SummaryUploadResult;

import java.util.List;
import java.util.Optional;

public interface EnrollmentSummaryStorage {
    SummaryUploadResult upload(String enrollmentId, byte[] content);

    SummaryUploadResult replace(String enrollmentId, byte[] content);

    Optional<StoredSummary> download(String enrollmentId);

    void delete(String enrollmentId);

    boolean exists(String enrollmentId);

    List<SummaryObjectInfo> listAll();
}
