package com.duoc.enrollmentplatform.factory;

import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryStorage;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.InMemoryEnrollmentSummaryStorage;
import com.duoc.enrollmentplatform.enrollment.infrastructure.adapters.S3EnrollmentSummaryStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class EnrollmentSummaryStorageConfiguration {

    @Bean
    @ConditionalOnProperty(name = "enrollment.summary.storage", havingValue = "in-memory", matchIfMissing = true)
    public EnrollmentSummaryStorage inMemoryEnrollmentSummaryStorage() {
        return new InMemoryEnrollmentSummaryStorage();
    }

    @Bean
    @ConditionalOnProperty(name = "enrollment.summary.storage", havingValue = "s3")
    public EnrollmentSummaryStorage s3EnrollmentSummaryStorage(
            S3Client s3Client,
            @Value("${aws.s3.bucket}") String bucket) {
        return new S3EnrollmentSummaryStorage(s3Client, bucket);
    }
}
