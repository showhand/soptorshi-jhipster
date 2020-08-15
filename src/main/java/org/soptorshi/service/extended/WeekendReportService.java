package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Weekend;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WeekendReportService {

    private final Logger log = LoggerFactory.getLogger(WeekendReportService.class);

    private final WeekendExtendedService weekendExtendedService;

    public static Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

    static Font TIME_ROMAN_11 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    static Font TIMES_BOLD_11 = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    static Font TIME_ROMAN_12 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    static Font TIME_BOLD_12 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    static Font TIMES_BOLD_14 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);

    public WeekendReportService(WeekendExtendedService weekendExtendedService) {
        this.weekendExtendedService = weekendExtendedService;
    }

    public ByteArrayInputStream getAllWeekends() throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 40, 40);
        document.addTitle("Holiday Type");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new headerAndFooter());
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;

        paragraph = new Paragraph(new Chunk("Seven Oceans Fish Processing Ltd", SoptorshiUtils.mBigBoldFont));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(new Chunk("Weekends", TIMES_BOLD_11));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        List<Weekend> weekends = weekendExtendedService.getAll()
            .stream()
            .sorted(Comparator.comparing(Weekend::getActiveFrom))
            .collect(Collectors.toList());

        pdfPTable = new PdfPTable(7);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{5, 13, 14, 14, 14, 24, 16});

        pdfPCell = new PdfPCell(new Paragraph("#", TIMES_BOLD_11));
        pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Day(s)", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Day 1", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Day 2", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Day 3", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("From", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Status", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        int counter = 0;

        for(Weekend weekend: weekends) {
            counter = counter + 1;
            pdfPCell = new PdfPCell(new Paragraph(counter + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(weekend.getNumberOfDays() + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(weekend.getDay1() == null ? "" : weekend.getDay1().toString(), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(weekend.getDay2() == null ? "" : weekend.getDay2().toString(), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(weekend.getDay3() == null ? "" : weekend.getDay3().toString(), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(weekend.getActiveFrom().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy")), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(weekend.getStatus().toString(), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
        }

        document.add(pdfPTable);

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    static class headerAndFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.BOLDITALIC, BaseColor.GRAY);
            DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            String loggedInUserId = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "Anonymous";
            Paragraph header =
                new Paragraph("Generated by " + loggedInUserId + " on " + new SimpleDateFormat("dd MMMM, yyyy").format(new Date()) + " at "
                    + dateFormat.format(new Date()), headerFont);
            header.setAlignment(Element.ALIGN_RIGHT);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(header),
                document.right() + 10, document.top() + 10, 0);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            String text = String.format("Page %s", writer.getCurrentPageNumber());
            Paragraph paragraph = new Paragraph(text, mBoldFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(paragraph), (document.right() - document.left())
                / 2 + document.leftMargin(), document.bottom() - 10, 0);
        }

    }

    void lineBreak(Paragraph p, int number) {
        for(int i = 0; i < number; i++) {
            p.add(new Paragraph(" "));
        }
    }
}
