package org.soptorshi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.soptorshi.domain.AccountBalance;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.domain.enumeration.GeneralLedgerFetchType;
import org.soptorshi.repository.extended.AccountBalanceExtendedRepository;
import org.soptorshi.repository.extended.DtTransactionExtendedRepository;
import org.soptorshi.repository.extended.MstAccountExtendedRepository;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.soptorshi.utils.SoptorshiUtils.mBoldFont;
import static org.soptorshi.utils.SoptorshiUtils.mLiteFont;

@Service
@Transactional(readOnly = true)
public class GeneralLedgerReportService {

    private AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private MstAccountExtendedRepository mstAccountExtendedRepository;

    public GeneralLedgerReportService(AccountBalanceExtendedRepository accountBalanceExtendedRepository, DtTransactionExtendedRepository dtTransactionExtendedRepository, MstAccountExtendedRepository mstAccountExtendedRepository) {
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
    }

    public ByteArrayInputStream createGeneralLedger(GeneralLedgerFetchType generalLedgerFetchType, Long accountId, LocalDate fromDate, LocalDate toDate) throws Exception, DocumentException {
        Map<Long, AccountBalance> accountBalanceMapWithAccount = accountBalanceExtendedRepository.findByFinancialAccountYear_Status(FinancialYearStatus.ACTIVE)
            .parallelStream()
            .collect(Collectors.toMap(a->a.getAccount().getId(), a->a));

        toDate = toDate.atTime(LocalTime.MAX).toLocalDate();

        List<DtTransaction> transactions = dtTransactionExtendedRepository.findByVoucherDateBetween(fromDate, toDate);
        Map<Long, List<DtTransaction>> transactionsMapWithAccount = transactions
            .stream()
            .collect(Collectors.groupingBy(t->t.getAccount().getId()));

        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        document.addTitle("General Ledger");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        document = createHeader(fromDate, toDate, document);

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private Document createHeader(LocalDate fromDate, LocalDate toDate, Document document) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100f);
        PdfPCell leftCell = new PdfPCell();
        Phrase innerPhrase = new Phrase();
        Paragraph paragraph = new Paragraph("Company: ", mBoldFont);
        innerPhrase.add(paragraph);

        paragraph = new Paragraph("Seven Oceans Fish Processing Limited", mLiteFont);
        innerPhrase.add(paragraph);
        leftCell.addElement(innerPhrase);
        leftCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(leftCell);

        PdfPCell rightCell = new PdfPCell();
        innerPhrase = new Phrase();
        paragraph = new Paragraph("Date : ", mBoldFont);
        paragraph.setFont(mBoldFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(SoptorshiUtils.formatDate(LocalDate.now(), "dd-MM-yyyy"), mLiteFont);
        paragraph.setFont(mLiteFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(innerPhrase);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(paragraph);

        innerPhrase = new Phrase();
        paragraph = new Paragraph("Time :", mBoldFont);
        paragraph.setFont(mBoldFont);
        innerPhrase.add(paragraph);
        LocalTime time = LocalTime.now();
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        paragraph = new Paragraph(SoptorshiUtils.formatDate(LocalDate.now(),"dd-MM-yyyy HH:mm aa"), mLiteFont);
        paragraph.setFont(mLiteFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(innerPhrase);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(paragraph);
        rightCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(rightCell);

        document.add(headerTable);

        Chunk chunk = new Chunk("General Ledger", mBoldFont);
        chunk.setUnderline(0.2f, -2f);
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        paragraph = new Paragraph("(Account Wise)", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        innerPhrase = new Phrase();
        paragraph = new Paragraph("Period :", mBoldFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(SoptorshiUtils.formatDate(fromDate, "dd-MM-yyyy"), mLiteFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(" to ", mBoldFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(SoptorshiUtils.formatDate(toDate, "dd-MM-yyyy"), mLiteFont);
        innerPhrase.add(paragraph);
        document.add(innerPhrase);
        return document;
    }
}
