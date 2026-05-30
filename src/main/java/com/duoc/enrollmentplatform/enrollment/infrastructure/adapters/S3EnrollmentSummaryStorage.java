package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import com.duoc.enrollmentplatform.enrollment.application.summary.StoredSummary;
import com.duoc.enrollmentplatform.enrollment.application.summary.SummaryObjectInfo;
import com.duoc.enrollmentplatform.enrollment.application.summary.SummaryUploadResult;
import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;
import com.duoc.enrollmentplatform.shared.domain.DomainError;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class S3EnrollmentSummaryStorage implements EnrollmentSummaryStorage {
    private static final String CONTENT_TYPE = "application/json";
    private static final String SUMMARY_FILENAME = "summary.json";

    private final S3Client s3Client;
    private final String bucket;

    public S3EnrollmentSummaryStorage(S3Client s3Client, String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public SummaryUploadResult upload(String enrollmentId, byte[] content) {
        putObject(enrollmentId, content);
        return resultFor(enrollmentId);
    }

    @Override
    public SummaryUploadResult replace(String enrollmentId, byte[] content) {
        putObject(enrollmentId, content);
        return resultFor(enrollmentId);
    }

    @Override
    public Optional<StoredSummary> download(String enrollmentId) {
        if (!exists(enrollmentId)) {
            return Optional.empty();
        }
        try {
            byte[] content = s3Client.getObject(GetObjectRequest.builder()
                            .bucket(bucket)
                            .key(objectKey(enrollmentId))
                            .build())
                    .readAllBytes();
            return Optional.of(new StoredSummary(content, CONTENT_TYPE));
        } catch (NoSuchKeyException error) {
            return Optional.empty();
        } catch (Exception error) {
            throw DomainError.other("Failed to download summary for enrollment " + enrollmentId);
        }
    }

    @Override
    public void delete(String enrollmentId) {
        if (!exists(enrollmentId)) {
            return;
        }
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey(enrollmentId))
                .build());
    }

    @Override
    public boolean exists(String enrollmentId) {
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey(enrollmentId))
                    .build());
            return true;
        } catch (NoSuchKeyException error) {
            return false;
        } catch (Exception error) {
            return false;
        }
    }

    @Override
    public List<SummaryObjectInfo> listAll() {
        List<SummaryObjectInfo> summaries = new ArrayList<>();
        s3Client.listObjectsV2Paginator(ListObjectsV2Request.builder().bucket(bucket).build())
                .stream()
                .flatMap(page -> page.contents().stream())
                .filter(object -> object.key().endsWith("/" + SUMMARY_FILENAME))
                .forEach(object -> summaries.add(toInfo(object)));
        return summaries;
    }

    private SummaryObjectInfo toInfo(S3Object object) {
        String key = object.key();
        String enrollmentId = key.substring(0, key.length() - ("/" + SUMMARY_FILENAME).length());
        return new SummaryObjectInfo(
                enrollmentId,
                key,
                object.size(),
                object.lastModified());
    }

    private void putObject(String enrollmentId, byte[] content) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(objectKey(enrollmentId))
                        .contentType(CONTENT_TYPE)
                        .build(),
                RequestBody.fromBytes(content));
    }

    private SummaryUploadResult resultFor(String enrollmentId) {
        return new SummaryUploadResult(enrollmentId, objectKey(enrollmentId), bucket);
    }

    private String objectKey(String enrollmentId) {
        return enrollmentId + "/" + SUMMARY_FILENAME;
    }
}
