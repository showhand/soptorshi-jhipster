package org.soptorshi.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
public class PurchaseOrderReportService {

    public ByteArrayInputStream createPurchaseOrderReport(Long purchaseOrderId) throws Exception, DocumentException {
        Document document = new Document();
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph("Hello world"));
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
