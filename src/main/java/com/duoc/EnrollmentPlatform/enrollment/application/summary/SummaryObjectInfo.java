package com.duoc.enrollmentplatform.enrollment.application.summary;

import java.time.Instant;

public class SummaryObjectInfo {
    public final String enrollmentId;
    public final String key;
    public final long size;
    public final Instant lastModified;

    public SummaryObjectInfo(String enrollmentId, String key, long size, Instant lastModified) {
        this.enrollmentId = enrollmentId;
        this.key = key;
        this.size = size;
        this.lastModified = lastModified;
    }
}
