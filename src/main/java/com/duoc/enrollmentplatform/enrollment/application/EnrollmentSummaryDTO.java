package com.duoc.enrollmentplatform.enrollment.application;

import java.util.List;

public class EnrollmentSummaryDTO {
    public String enrollmentId;
    public String studentId;
    public List<EnrollmentLineDTO> lines;
    public double totalAmount;

    public EnrollmentSummaryDTO(String enrollmentId, String studentId, List<EnrollmentLineDTO> lines, double totalAmount) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.lines = lines;
        this.totalAmount = totalAmount;
    }
}
