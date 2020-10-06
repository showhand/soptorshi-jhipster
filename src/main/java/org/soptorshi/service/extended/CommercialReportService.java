package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommercialReportService {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeReportService.class);

    private final CommercialBudgetExtendedService commercialBudgetExtendedService;

    private final CommercialProductInfoExtendedService commercialProductInfoExtendedService;

    private final CommercialPiExtendedService commercialPiExtendedService;

    private final CommercialPoExtendedService commercialPoExtendedService;

    private final CommercialPaymentInfoExtendedService commercialPaymentInfoExtendedService;

    public static Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

    static Font TIME_ROMAN_11 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    static Font TIMES_BOLD_11 = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    static Font TIME_ROMAN_12 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    static Font TIME_BOLD_12 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    static Font TIMES_BOLD_14 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);

    public CommercialReportService(CommercialBudgetExtendedService commercialBudgetExtendedService, CommercialProductInfoExtendedService commercialProductInfoExtendedService, CommercialPiExtendedService commercialPiExtendedService, CommercialPoExtendedService commercialPoExtendedService, CommercialPaymentInfoExtendedService commercialPaymentInfoExtendedService) {
        this.commercialBudgetExtendedService = commercialBudgetExtendedService;
        this.commercialProductInfoExtendedService = commercialProductInfoExtendedService;
        this.commercialPiExtendedService = commercialPiExtendedService;
        this.commercialPoExtendedService = commercialPoExtendedService;
        this.commercialPaymentInfoExtendedService = commercialPaymentInfoExtendedService;
    }

    public ByteArrayInputStream getCommercialBudgets() throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 40, 40);
        document.addTitle("Commercial Budget");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new headerAndFooter());
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPTable innerPdfPTable = null;
        PdfPCell pdfPCell = null;
        PdfPCell innerPdfPCell = null;

        paragraph = new Paragraph(new Chunk("Seven Oceans Fish Processing Ltd", SoptorshiUtils.mBigBoldFont));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(new Chunk("Commercial Budgets", TIMES_BOLD_11));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        List<CommercialBudget> commercialBudgets = commercialBudgetExtendedService.getAll();

        pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{5, 20, 15, 25, 35});

        pdfPCell = new PdfPCell(new Paragraph("#", TIMES_BOLD_11));
        pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Budget No", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Type", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Budget Date", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Status", TIMES_BOLD_11));
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        int counter = 0;

        for(CommercialBudget commercialBudget: commercialBudgets) {
            counter = counter + 1;
            pdfPCell = new PdfPCell(new Paragraph(counter + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(commercialBudget.getBudgetNo(), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(commercialBudget.getType().toString(), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(commercialBudget.getBudgetDate().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy")), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(commercialBudget.getBudgetStatus().toString(), TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            List<CommercialProductInfoDTO> commercialProductInfoDTOs = commercialProductInfoExtendedService.getAllProductInfoByBudgetId(commercialBudget.getId());

            pdfPCell = new PdfPCell(new Paragraph("Company Name: " + commercialBudget.getCompanyName(), TIME_ROMAN_11));
            pdfPCell.setRowspan(commercialProductInfoDTOs.size() + 3);
            pdfPCell.setColspan(5);

            innerPdfPTable = new PdfPTable(9);
            innerPdfPTable.setWidthPercentage(100);
            innerPdfPTable.setTotalWidth(new float[]{11, 11, 11, 11, 11, 11, 11, 11, 12});

            innerPdfPCell = new PdfPCell(new Paragraph("Task", TIMES_BOLD_11));
            innerPdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Product", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Product Category", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Offered", TIMES_BOLD_11));
            innerPdfPCell.setColspan(3);
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Buying", TIMES_BOLD_11));
            innerPdfPCell.setColspan(3);
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell();
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell();
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell();
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Quantity", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Unit Price", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Total Price", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Quantity", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Unit Price", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            innerPdfPCell = new PdfPCell(new Paragraph("Total Price", TIMES_BOLD_11));
            innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            innerPdfPTable.addCell(innerPdfPCell);

            for(CommercialProductInfoDTO commercialProductInfoDTO: commercialProductInfoDTOs) {

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getTaskNo().toString(), TIMES_BOLD_11));
                innerPdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getProductsName(), TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getProductCategoriesName(), TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getOfferedQuantity().toString() + " (" + commercialProductInfoDTO.getOfferedUnit().toString() + ")", TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getOfferedUnitPrice().toString(), TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getOfferedTotalPrice().toString(), TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getBuyingQuantity().toString() + " (" + commercialProductInfoDTO.getOfferedUnit().toString() + ")", TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getBuyingUnitPrice().toString(), TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);

                innerPdfPCell = new PdfPCell(new Paragraph(commercialProductInfoDTO.getBuyingTotalPrice().toString(), TIMES_BOLD_11));
                innerPdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                innerPdfPTable.addCell(innerPdfPCell);
            }
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
