package org.soptorshi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SelectionType;
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.extended.QuotationDetailsExtendedRepository;
import org.soptorshi.repository.extended.QuotationExtendedRepository;
import org.soptorshi.repository.extended.RequisitionDetailsExtendedRepository;
import org.soptorshi.repository.extended.TermsAndConditionsExtendedRepository;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderReportService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    QuotationExtendedRepository quotationExtendedRepository;
    @Autowired
    QuotationDetailsExtendedRepository quotationDetailsExtendedRepository;
    @Autowired
    RequisitionDetailsExtendedRepository requisitionDetailsExtendedRepository;
    @Autowired
    TermsAndConditionsExtendedRepository termsAndConditionsExtendedRepository;


    @Transactional(readOnly = true)
    public ByteArrayInputStream createPurchaseOrderReport(Long purchaseOrderId) throws Exception, DocumentException {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.getOne(purchaseOrderId);
        List<RequisitionDetails> requisitionDetails = requisitionDetailsExtendedRepository.getByRequisition(purchaseOrder.getRequisition());
        Map<Long, RequisitionDetails> productMapWithRequisitionDetails = new HashMap<>();
        for(RequisitionDetails r: requisitionDetails){
            productMapWithRequisitionDetails.put(r.getProduct().getId(), r);
        }
        Document document = new Document();
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        HeaderAndFotter headerAndFotter = new HeaderAndFotter();
        writer.setPageEvent(headerAndFotter);
        document.open();
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        Paragraph paragraph = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        document.add(table);

        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell leftCell = new PdfPCell();
        leftCell.addElement(new Paragraph("Date: "+ SoptorshiUtils.formatDate(purchaseOrder.getIssueDate(),"dd-MM-yyyy"),FontFactory.getFont(FontFactory.TIMES_BOLD) ));
        leftCell.addElement(new Paragraph(""));
        leftCell.addElement(new Paragraph("To", FontFactory.getFont(FontFactory.TIMES_BOLD)));
//        leftCell.addElement(new Paragraph(purchaseOrder.getReferredTo(), FontFactory.getFont(FontFactory.TIMES)));
        leftCell.addElement(new Paragraph(purchaseOrder.getQuotation().getVendor().getCompanyName(), FontFactory.getFont(FontFactory.TIMES)));
        leftCell.addElement(new Paragraph(purchaseOrder.getQuotation().getVendor().getAddress(), FontFactory.getFont(FontFactory.TIMES)));
        leftCell.addElement(new Paragraph(purchaseOrder.getQuotation().getVendor().getContactNumber(), FontFactory.getFont(FontFactory.TIMES)));
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

        paragraph = new Paragraph("Subject: "+ purchaseOrder.getSubject(), FontFactory.getFont(FontFactory.TIMES_BOLD));
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(purchaseOrder.getReferredTo()+",", FontFactory.getFont(FontFactory.TIMES_BOLD));
        document.add(paragraph);

        paragraph = new Paragraph(purchaseOrder.getNote(), FontFactory.getFont(FontFactory.TIMES));
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        float[] tableColumnWidth = new float[]{0.5f, 5f, 1.7f, 1.8f, 1.8f, 1f, 2f, 2f, 3F};
        table = new PdfPTable(tableColumnWidth);
        table.setWidthPercentage(100);

        paragraph = new Paragraph("Sl.", FontFactory.getFont(FontFactory.TIMES_BOLD, 9f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        table.addCell(new PdfPCell(new Paragraph("Product Specification", FontFactory.getFont(FontFactory.TIMES_BOLD, 9f))));
        paragraph = new Paragraph("ETD", FontFactory.getFont(FontFactory.TIMES_BOLD,9f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("VAT", FontFactory.getFont(FontFactory.TIMES_BOLD,9f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("AIT", FontFactory.getFont(FontFactory.TIMES_BOLD, 9f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("UOM", FontFactory.getFont(FontFactory.TIMES_BOLD, 9f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("Quantity", FontFactory.getFont(FontFactory.TIMES_BOLD, 9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("Unit Price", FontFactory.getFont(FontFactory.TIMES_BOLD, 9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(paragraph));
        paragraph = new Paragraph("Total Amount", FontFactory.getFont(FontFactory.TIMES_BOLD, 9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        table.addCell(new PdfPCell(paragraph));


        List<QuotationDetails> quotationDetailsList = quotationDetailsExtendedRepository.findByQuotationId(purchaseOrder.getQuotation().getId());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for(int i=0; i<quotationDetailsList.size(); i++){
            paragraph = new Paragraph((i+1)+"", FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(quotationDetailsList.get(i).getProduct().getName(), FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(SoptorshiUtils.formatDate(quotationDetailsList.get(i).getEstimatedDate(),"dd-MM-yyyy"), FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(quotationDetailsList.get(i).getVatPercentage()==null?"":quotationDetailsList.get(i).getVatPercentage().toString(), FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(quotationDetailsList.get(i).getAitPercentage()==null?"": quotationDetailsList.get(i).getAitPercentage().toString(), FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(productMapWithRequisitionDetails.get(quotationDetailsList.get(i).getProduct().getId()).getUom().toString(), FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(quotationDetailsList.get(i).getQuantity().toString(), FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(quotationDetailsList.get(i).getRate().toString(), FontFactory.getFont(FontFactory.TIMES,9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));

            paragraph = new Paragraph(quotationDetailsList.get(i).getRate().multiply( new BigDecimal( quotationDetailsList.get(i).getQuantity())).toString(), FontFactory.getFont(FontFactory.TIMES, 9f));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(paragraph));
            BigDecimal productTotalAmount = BigDecimal.ZERO;
            productTotalAmount = quotationDetailsList.get(i).getRate().multiply( new BigDecimal( quotationDetailsList.get(i).getQuantity()));
            BigDecimal vat = BigDecimal.ZERO;
            BigDecimal ait = BigDecimal.ZERO;
            if(quotationDetailsList.get(i).getVatPercentage()!=null){
                vat = productTotalAmount.add(quotationDetailsList.get(i).getVatPercentage().divide(new BigDecimal(100)));
            }
            if(quotationDetailsList.get(i).getAitPercentage()!=null){
                ait = productTotalAmount.add(quotationDetailsList.get(i).getAitPercentage().divide(new BigDecimal(100)));
            }
            productTotalAmount = productTotalAmount.add(vat).subtract(ait);
            totalAmount = totalAmount.add(productTotalAmount);
        }

        paragraph = new Paragraph("Total", FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setColspan(8);
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);

        paragraph = new Paragraph(SoptorshiUtils.getFormattedBalance(totalAmount), FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);


        paragraph = new Paragraph("Labor/Other", FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setColspan(8);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        BigDecimal laborAmount = purchaseOrder.getLaborOrOtherAmount()==null?BigDecimal.ZERO:purchaseOrder.getLaborOrOtherAmount();
        paragraph = new Paragraph(SoptorshiUtils.getFormattedBalance(purchaseOrder.getLaborOrOtherAmount()==null?BigDecimal.ZERO:purchaseOrder.getLaborOrOtherAmount()), FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        paragraph = new Paragraph("Gross Amount", FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setColspan(8);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        BigDecimal grossAmount = totalAmount.add(laborAmount);
        paragraph = new Paragraph(SoptorshiUtils.getFormattedBalance(totalAmount.add(laborAmount)), FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);


        paragraph = new Paragraph("Discount", FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setColspan(8);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        BigDecimal discount = (totalAmount.multiply(purchaseOrder.getDiscount()==null?BigDecimal.ZERO: new BigDecimal( purchaseOrder.getDiscount()))).divide(new BigDecimal(100));
        paragraph = new Paragraph(SoptorshiUtils.getFormattedBalance(discount), FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);


        paragraph = new Paragraph("Net Amount", FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setColspan(8);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        BigDecimal netAmount = grossAmount.add(discount);
        paragraph = new Paragraph(SoptorshiUtils.getFormattedBalance(netAmount), FontFactory.getFont(FontFactory.TIMES,9f));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);

        document.add(new Paragraph("Amount in words: "+ SoptorshiUtils.convertNumberToWords(netAmount),FontFactory.getFont(FontFactory.TIMES_BOLD,11f)));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Terms and Conditions: ",FontFactory.getFont(FontFactory.TIMES_BOLD,11f)));

        List<TermsAndConditions> termsAndConditions = termsAndConditionsExtendedRepository.getByPurchaseOrder(purchaseOrder);
        Chunk bullet = new Chunk("\u2022",FontFactory.getFont(FontFactory.TIMES,11f) );
        com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
        list.setListSymbol(bullet);
        for(TermsAndConditions t: termsAndConditions){
            list.add(new ListItem(new Paragraph(t.getDescription(), FontFactory.getFont(FontFactory.TIMES,11f))));
        }
        document.add(list);
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    class HeaderAndFotter extends PdfPageEventHelper{


        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(3);
            PdfPCell cell = new PdfPCell();
            Paragraph paragraph = new Paragraph("Prepared By", FontFactory.getFont(FontFactory.TIMES,11f));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph("", FontFactory.getFont(FontFactory.TIMES,11f));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);


            cell = new PdfPCell();
            paragraph = new Paragraph("Supplier Sign", FontFactory.getFont(FontFactory.TIMES,11f));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);
            try{
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);

                document.add(table);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
