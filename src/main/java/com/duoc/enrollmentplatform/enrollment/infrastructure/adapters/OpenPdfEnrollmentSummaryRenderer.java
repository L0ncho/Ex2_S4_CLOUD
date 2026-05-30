package com.duoc.enrollmentplatform.enrollment.infrastructure.adapters;

import com.duoc.enrollmentplatform.enrollment.application.EnrollmentLineDTO;
import com.duoc.enrollmentplatform.enrollment.application.summary.EnrollmentSummaryDocumentDTO;
import com.duoc.enrollmentplatform.enrollment.application.ports.EnrollmentSummaryPdfRenderer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class OpenPdfEnrollmentSummaryRenderer implements EnrollmentSummaryPdfRenderer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] render(byte[] summaryJson) {
        try {
            EnrollmentSummaryDocumentDTO document = objectMapper.readValue(summaryJson, EnrollmentSummaryDocumentDTO.class);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, output);
            pdfDocument.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            pdfDocument.add(new Paragraph("Resumen de inscripción", titleFont));
            pdfDocument.add(new Paragraph("ID de inscripción: " + document.enrollmentId, bodyFont));
            pdfDocument.add(new Paragraph("Estudiante: " + document.studentFullName + " (" + document.studentEmail + ")", bodyFont));
            pdfDocument.add(new Paragraph("Fecha de inscripción: " + document.enrolledAt, bodyFont));
            pdfDocument.add(new Paragraph(" ", bodyFont));

            for (EnrollmentLineDTO line : document.lines) {
                pdfDocument.add(new Paragraph(
                        "- " + line.courseName + " (" + line.courseId + "): $" + line.unitPrice,
                        bodyFont));
            }

            pdfDocument.add(new Paragraph("Total a pagar: $" + document.totalAmount, titleFont));
            pdfDocument.close();
            return output.toByteArray();
        } catch (Exception error) {
            throw new IllegalStateException("Failed to render enrollment summary PDF", error);
        }
    }
}
