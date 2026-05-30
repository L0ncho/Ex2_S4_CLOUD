package com.duoc.enrollmentplatform.enrollment.application.summary;

public class SummaryUploadResult {
    public final String enrollmentId;
    public final String s3Key;
    public final String bucket;

    public SummaryUploadResult(String enrollmentId, String s3Key, String bucket) {
        this.enrollmentId = enrollmentId;
        this.s3Key = s3Key;
        this.bucket = bucket;
    }
}
