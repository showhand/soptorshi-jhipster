package org.soptorshi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.repository.extended.DtTransactionExtendedRepository;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.font.FontFamily;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoucherReport {

    private DtTransactionExtendedRepository dtTransactionExtendedRepository;

    public VoucherReport(DtTransactionExtendedRepository dtTransactionExtendedRepository) {
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
    }

    public ByteArrayInputStream createVoucherReport(String voucherName, String voucherNo, LocalDate voucherDate) throws Exception, DocumentException {

        List<DtTransaction> dtTransactions = dtTransactionExtendedRepository.findByVoucherNoAndVoucherDate(voucherNo, voucherDate);

        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        document.addTitle("Voucher Report");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Paragraph paragraph = new Paragraph("Seven Oceans Fish Processing Ltd.", SoptorshiUtils.mBigBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        SoptorshiPdfCell cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Voucher No :"+voucherNo, SoptorshiUtils.mLiteFont));
        cell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(cell);

        cell = new SoptorshiPdfCell();
        paragraph = new Paragraph(voucherName, SoptorshiUtils.mBoldFontUnderLine);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(cell);

        cell = new SoptorshiPdfCell();
        paragraph = new Paragraph("Voucher Date : "+SoptorshiUtils.formatDate(dtTransactions.get(0).getVoucherDate(),"dd-MM-yyyy"), SoptorshiUtils.mLiteFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        paragraph = new Paragraph("Post Date : "+SoptorshiUtils.formatDate(dtTransactions.get(0).getPostDate(), "dd-MM-yyyy"), SoptorshiUtils.mLiteFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);
        document.add(headerTable);

        float[] tableColumnWidth = new float[]{0.7f,5f,1.5f,2f,2f,2f};
        PdfPTable voucherBody = new PdfPTable(tableColumnWidth);
        voucherBody.setWidthPercentage(100);
        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("S. No", SoptorshiUtils.mBoldFont));
        voucherBody.addCell(cell);

        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Head of Account", SoptorshiUtils.mBoldFont));
        voucherBody.addCell(cell);

        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Reference", SoptorshiUtils.mBoldFont));
        voucherBody.addCell(cell);

        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Cheque No & Date", SoptorshiUtils.mBoldFont));
        voucherBody.addCell(cell);

        cell = new SoptorshiPdfCell(new Paragraph("Debit", SoptorshiUtils.mBoldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        voucherBody.addCell(cell);


        cell = new SoptorshiPdfCell(new Paragraph("Credit", SoptorshiUtils.mBoldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        voucherBody.addCell(cell);
        int serialNo = 0;
        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;
        for(DtTransaction dtTransaction: dtTransactions){
            serialNo+=1;
            cell = new SoptorshiPdfCell();
            cell.addElement(new Paragraph(serialNo+"", SoptorshiUtils.mLiteFont));
            voucherBody.addCell(cell);

            cell = new SoptorshiPdfCell();
            cell.addElement(new Paragraph(dtTransaction.getAccount().getName(), SoptorshiUtils.mLiteFont));
            voucherBody.addCell(cell);

            cell = new SoptorshiPdfCell();
            cell.addElement(new Paragraph(dtTransaction.getReference(), SoptorshiUtils.mLiteFont));
            voucherBody.addCell(cell);

            cell = new SoptorshiPdfCell();
            cell.addElement(new Paragraph(dtTransaction.getInstrumentNo()==null?"":  dtTransaction.getInstrumentNo()+dtTransaction.getInstrumentDate()==null?"":"("+SoptorshiUtils.formatDate(dtTransaction.getInstrumentDate(),"dd-MM-yyyy")+")", SoptorshiUtils.mLiteFont));
            voucherBody.addCell(cell);

            cell = new SoptorshiPdfCell(new Paragraph(dtTransaction.getBalanceType().equals(BalanceType.DEBIT)?SoptorshiUtils.getFormattedBalance(dtTransaction.getAmount()):"", SoptorshiUtils.mLiteFont));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            voucherBody.addCell(cell);


            cell = new SoptorshiPdfCell(new Paragraph(dtTransaction.getBalanceType().equals(BalanceType.CREDIT)?SoptorshiUtils.getFormattedBalance(dtTransaction.getAmount()):"", SoptorshiUtils.mLiteFont));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            voucherBody.addCell(cell);

            if(dtTransaction.getBalanceType().equals(BalanceType.DEBIT))
                totalDebit = totalDebit.add(dtTransaction.getAmount());
            else
                totalCredit = totalCredit.add(dtTransaction.getAmount());
        }

        cell = new SoptorshiPdfCell(new Paragraph("Total", SoptorshiUtils.mBoldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setColspan(4);
        voucherBody.addCell(cell);

        cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalDebit), SoptorshiUtils.mBoldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        voucherBody.addCell(cell);

        cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalCredit), SoptorshiUtils.mBoldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        voucherBody.addCell(cell);

        document.add(voucherBody);

        document.add(Chunk.NEWLINE);
        paragraph = new Paragraph("In words : "+SoptorshiUtils.convertNumberToWords(totalDebit),SoptorshiUtils.mBoldFont);
        document.add(paragraph);

        paragraph = new Paragraph("Narration :"+dtTransactions.get(0).getNarration(), SoptorshiUtils.mBoldFont);
        document.add(paragraph);

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

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
