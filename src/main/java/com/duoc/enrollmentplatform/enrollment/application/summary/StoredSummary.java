package com.duoc.enrollmentplatform.enrollment.application.summary;

public class StoredSummary {
    public final byte[] content;
    public final String contentType;

    public StoredSummary(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }
}
