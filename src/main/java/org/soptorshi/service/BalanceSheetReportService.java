package org.soptorshi.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.soptorshi.domain.enumeration.BalanceSheetFetchType;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class BalanceSheetReportService {
    private MstGroupExtendedRepository mstGroupExtendedRepository;
    private MstAccountRepository mstAccountRepository;
    private SystemGroupMapRepository systemGroupMapRepository;

    public BalanceSheetReportService(MstGroupExtendedRepository mstGroupExtendedRepository, MstAccountRepository mstAccountRepository, SystemGroupMapRepository systemGroupMapRepository) {
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.mstAccountRepository = mstAccountRepository;
        this.systemGroupMapRepository = systemGroupMapRepository;
    }

    public ByteArrayInputStream createBalanceSheetReport(BalanceSheetFetchType balanceSheetFetchType, LocalDate asOnDate) throws Exception, DocumentException {

        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Balance sheet report"));

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
