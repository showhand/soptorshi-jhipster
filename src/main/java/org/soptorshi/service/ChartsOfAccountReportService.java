package org.soptorshi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@Transactional(readOnly = true)
public class ChartsOfAccountReportService {
    public ByteArrayInputStream createChartsOrAccountReport() throws Exception, DocumentException{
        Document document = new Document();
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Paragraph paragraph = new Paragraph("Seven Oceans Fish Processing Ltd.", SoptorshiUtils.mBigBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        paragraph = new Paragraph("Chart of Accounts", SoptorshiUtils.mBigLiteFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        float[] tableColumnWidth = new float[]{1,2};
        PdfPTable table = new PdfPTable(tableColumnWidth);

        table.setWidthPercentage(100);

        SoptorshiPdfCell cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Account Category", SoptorshiUtils.mBoldFont));
        table.addCell(cell);

        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Account Title", SoptorshiUtils.mBoldFont));
        table.addCell(cell);

        document.add(Chunk.NEWLINE);
        document.add(table);
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
