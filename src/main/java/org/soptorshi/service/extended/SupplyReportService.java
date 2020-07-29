package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SupplyReportService {

    private final Logger log = LoggerFactory.getLogger(SupplyReportService.class);

    private final SupplyZoneExtendedService supplyZoneExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    private final SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService;

    private final SupplyShopExtendedService supplyShopExtendedService;

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    private final SupplyAreaWiseAccumulationExtendedService supplyAreaWiseAccumulationExtendedService;

    private final SupplyZoneWiseAccumulationExtendedService supplyZoneWiseAccumulationExtendedService;

    public SupplyReportService(SupplyZoneExtendedService supplyZoneExtendedService, SupplyAreaExtendedService supplyAreaExtendedService,
                               SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService,
                               SupplyShopExtendedService supplyShopExtendedService, SupplyOrderExtendedService supplyOrderExtendedService,
                               SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService, SupplyAreaWiseAccumulationExtendedService supplyAreaWiseAccumulationExtendedService, SupplyZoneWiseAccumulationExtendedService supplyZoneWiseAccumulationExtendedService) {

        this.supplyZoneExtendedService = supplyZoneExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
        this.supplySalesRepresentativeExtendedService = supplySalesRepresentativeExtendedService;
        this.supplyShopExtendedService = supplyShopExtendedService;
        this.supplyOrderExtendedService = supplyOrderExtendedService;
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
        this.supplyAreaWiseAccumulationExtendedService = supplyAreaWiseAccumulationExtendedService;
        this.supplyZoneWiseAccumulationExtendedService = supplyZoneWiseAccumulationExtendedService;

    }

    @Transactional
    public ByteArrayInputStream downloadReport(LocalDate from, LocalDate to) throws Exception, DocumentException {

        List<SupplyOrder> supplyOrders = supplyOrderExtendedService.getAllByDateOfOrderBeforeAndDateOfOrderAfter(from, to);

        BigDecimal totalCloseOrder = BigDecimal.ZERO;
        BigDecimal totalProcessingOrder = BigDecimal.ZERO;
        BigDecimal totalWaitingForMoney = BigDecimal.ZERO;
        BigDecimal totalPending = BigDecimal.ZERO;

        BigDecimal totalCloseOrderAmount = BigDecimal.ZERO;
        BigDecimal totalProcessingOrderAmount = BigDecimal.ZERO;
        BigDecimal totalWaitingForMoneyAmount = BigDecimal.ZERO;
        BigDecimal totalPendingAmount = BigDecimal.ZERO;

        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        document.addTitle("Summary Report");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Paragraph paragraph = new Paragraph("7 Oceans Ltd.", SoptorshiUtils.mBigBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph("Summary: (" + from.toString() + "to" + to.toString() + ")", SoptorshiUtils.mBoldFont);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN)) {
            PdfPTable pdfPTable = new PdfPTable(8);
            PdfPCell cell = new PdfPCell(new Paragraph("Order No", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Zone", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Area", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Product Category", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Product", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Quantity", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Price", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Status", SoptorshiUtils.mBoldFont));
            pdfPTable.addCell(cell);
            for(SupplyOrder supplyOrder: supplyOrders) {
                List<SupplyOrderDetails> supplyOrderDetails = new ArrayList<>();
                supplyOrderDetails = supplyOrderDetailsExtendedService.getAllBySupplyOrder(supplyOrder);
                cell = new PdfPCell(new Paragraph(supplyOrder.getOrderNo(), SoptorshiUtils.mLiteMediumFont));
                pdfPTable.addCell(cell);
                cell = new PdfPCell(new Paragraph(supplyOrder.getSupplyZone().getName(), SoptorshiUtils.mLiteMediumFont));
                pdfPTable.addCell(cell);
                cell = new PdfPCell(new Paragraph(supplyOrder.getSupplyArea().getName(), SoptorshiUtils.mLiteMediumFont));
                pdfPTable.addCell(cell);
                cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                cell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell);
                cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                cell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell);
                cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                cell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell);
                cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                cell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell);
                cell = new PdfPCell(new Paragraph(supplyOrder.getStatus().name(), SoptorshiUtils.mLiteMediumFont));
                pdfPTable.addCell(cell);

                boolean orderCloseStatus = false;
                boolean orderReceivedStatus = false;
                boolean orderMoneyWaitingStatus = false;
                boolean orderProcessing = false;

                if(supplyOrder.getStatus().equals(SupplyOrderStatus.ORDER_RECEIVED)) {
                    orderReceivedStatus = true;
                    orderCloseStatus = false;
                    orderMoneyWaitingStatus = false;
                    orderProcessing = false;
                }
                else if (supplyOrder.getStatus().equals(SupplyOrderStatus.PROCESSING_ORDER)){
                    orderProcessing = true;
                    orderCloseStatus = false;
                    orderMoneyWaitingStatus = false;
                    orderReceivedStatus = false;
                }
                else if (supplyOrder.getStatus().equals(SupplyOrderStatus.ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION)){
                    orderProcessing = false;
                    orderCloseStatus = false;
                    orderMoneyWaitingStatus = true;
                    orderReceivedStatus = false;
                }else if (supplyOrder.getStatus().equals(SupplyOrderStatus.ORDER_CLOSE)){
                    orderProcessing = false;
                    orderCloseStatus = true;
                    orderMoneyWaitingStatus = false;
                    orderReceivedStatus = false;
                }



                for(SupplyOrderDetails supplyOrderDetails1: supplyOrderDetails) {
                    cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    pdfPTable.addCell(cell);
                    cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    pdfPTable.addCell(cell);
                    cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    pdfPTable.addCell(cell);

                    cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getProductCategory().getName(), SoptorshiUtils.mLiteMediumFont));
                    pdfPTable.addCell(cell);
                    cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getProduct().getName(), SoptorshiUtils.mLiteMediumFont));
                    pdfPTable.addCell(cell);
                    cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getQuantity().toString(), SoptorshiUtils.mLiteMediumFont));
                    pdfPTable.addCell(cell);
                    cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getPrice().toString(), SoptorshiUtils.mLiteMediumFont));
                    pdfPTable.addCell(cell);
                    cell = new PdfPCell(new Paragraph("", SoptorshiUtils.mLiteMediumFont));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    pdfPTable.addCell(cell);

                    if(orderCloseStatus) {
                        totalCloseOrder = totalCloseOrder.add(supplyOrderDetails1.getQuantity());
                        totalCloseOrderAmount = totalCloseOrderAmount.add(supplyOrderDetails1.getPrice());
                    }
                    else if(orderMoneyWaitingStatus){
                        totalWaitingForMoney = totalWaitingForMoney.add(supplyOrderDetails1.getQuantity());
                        totalWaitingForMoneyAmount = totalWaitingForMoneyAmount.add(supplyOrderDetails1.getPrice());
                    }
                    else if(orderProcessing) {
                        totalProcessingOrder = totalProcessingOrder.add(supplyOrderDetails1.getQuantity());
                        totalProcessingOrderAmount = totalProcessingOrderAmount.add(supplyOrderDetails1.getPrice());
                    }else if(orderReceivedStatus) {
                        totalPending = totalPending.add(supplyOrderDetails1.getQuantity());
                        totalPendingAmount = totalPendingAmount.add(supplyOrderDetails1.getPrice());
                    }
                }
            }
            document.add(pdfPTable);

            paragraph = new Paragraph("In process: " + totalProcessingOrder + " (" + totalProcessingOrderAmount + "Taka )" , SoptorshiUtils.mLiteMediumFont);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);

            paragraph = new Paragraph("Waiting for money collection: " + totalWaitingForMoney + " (" + totalWaitingForMoneyAmount + "Taka )", SoptorshiUtils.mLiteMediumFont);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);

            paragraph = new Paragraph("Order Close: " + totalCloseOrder + " (" + totalCloseOrderAmount + "Taka )", SoptorshiUtils.mLiteMediumFont);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
        }
        else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {

        }
        else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {

        }

        document = createAuthorizationSection(document);

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }


    private Document createAuthorizationSection(Document pDocument) throws Exception {

        PdfPTable pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100);
        SoptorshiPdfCell cell = new SoptorshiPdfCell();

        Paragraph paragraph = new Paragraph("RECEIVED BY", SoptorshiUtils.mLiteFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(cell);

        cell = new SoptorshiPdfCell();
        paragraph = new Paragraph("PREPARED BY", SoptorshiUtils.mLiteFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(cell);

        cell = new SoptorshiPdfCell();
        paragraph = new Paragraph("AUTHORIZED BY", SoptorshiUtils.mLiteFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(cell);

        pdfPTable.setSpacingBefore(100);
        pDocument.add(pdfPTable);
        return pDocument;
    }
}
