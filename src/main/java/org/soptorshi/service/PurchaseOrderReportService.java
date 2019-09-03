package org.soptorshi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.soptorshi.domain.PurchaseOrder;
import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.QuotationDetails;
import org.soptorshi.domain.enumeration.SelectionType;
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.extended.QuotationDetailsExtendedRepository;
import org.soptorshi.repository.extended.QuotationExtendedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class PurchaseOrderReportService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    QuotationExtendedRepository quotationExtendedRepository;
    @Autowired
    QuotationDetailsExtendedRepository quotationDetailsExtendedRepository;


    @Transactional(readOnly = true)
    public ByteArrayInputStream createPurchaseOrderReport(Long purchaseOrderId) throws Exception, DocumentException {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.getOne(purchaseOrderId);
        Document document = new Document();
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        Paragraph paragraph = new Paragraph("Seven Oceans Fish Processing LTD", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        document.add(table);

        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell leftCell = new PdfPCell();
        leftCell.addElement(new Paragraph("To", FontFactory.getFont(FontFactory.TIMES_BOLD)));
//        leftCell.addElement(new Paragraph(purchaseOrder.getReferredTo(), FontFactory.getFont(FontFactory.TIMES)));
        leftCell.addElement(new Paragraph(purchaseOrder.getQuotation().getVendor().getCompanyName(), FontFactory.getFont(FontFactory.TIMES)));
        leftCell.addElement(new Paragraph(purchaseOrder.getQuotation().getVendor().getDescription(), FontFactory.getFont(FontFactory.TIMES)));
        leftCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(leftCell);

        PdfPCell rightCell = new PdfPCell();
        paragraph = new Paragraph("Purchase Order", FontFactory.getFont(FontFactory.TIMES));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(paragraph);
        paragraph = new Paragraph("Reference No "+purchaseOrder.getPurchaseOrderNo(), FontFactory.getFont(FontFactory.TIMES));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(paragraph);
        paragraph = new Paragraph("Requisition No "+purchaseOrder.getRequisition().getRequisitionNo(), FontFactory.getFont(FontFactory.TIMES));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(paragraph);
        paragraph = new Paragraph("Quotation No "+purchaseOrder.getQuotation().getQuotationNo(), FontFactory.getFont(FontFactory.TIMES));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(paragraph);
        rightCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(rightCell);

        document.add(table);

        paragraph = new Paragraph(purchaseOrder.getSubject(), FontFactory.getFont(FontFactory.TIMES_BOLD));
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(purchaseOrder.getReferredTo()+",", FontFactory.getFont(FontFactory.TIMES_BOLD));
        document.add(paragraph);

        paragraph = new Paragraph(purchaseOrder.getNote(), FontFactory.getFont(FontFactory.TIMES_BOLD));
        document.add(paragraph);

        table = new PdfPTable(9);
        table.setWidthPercentage(100);

        paragraph = new Paragraph("Sl.", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        table.addCell(new PdfPCell(new Paragraph("Product Specification", FontFactory.getFont(FontFactory.TIMES_BOLD))));
        paragraph = new Paragraph("ETD", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("VAT", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("AIT", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("UOM", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("Quantity", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("Unit Price", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("Total Amount", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(paragraph));


        List<QuotationDetails> quotationDetailsList = quotationDetailsExtendedRepository.findByQuotationId(purchaseOrder.getQuotation().getId());
        for(int i=0; i<quotationDetailsList.size(); i++){
            paragraph = new Paragraph((i+1)+"", FontFactory.getFont(FontFactory.TIMES));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(quotationDetailsList.get(i).getProduct().getName(), FontFactory.getFont(FontFactory.TIMES));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            //paragraph = new Paragraph(quotationDetailsList.get(i)., FontFactory.getFont(FontFactory.TIMES));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

        }

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
