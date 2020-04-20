package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.repository.extended.SupplyChallanExtendedRepository;
import org.soptorshi.repository.search.SupplyChallanSearchRepository;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.service.SupplyChallanService;
import org.soptorshi.service.mapper.SupplyChallanMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing SupplyChallan.
 */
@Service
@Transactional
public class SupplyChallanExtendedService extends SupplyChallanService {

    private final Logger log = LoggerFactory.getLogger(SupplyChallanExtendedService.class);

    private final SupplyChallanExtendedRepository supplyChallanExtendedRepository;

    private final SupplyChallanMapper supplyChallanMapper;

    private final SupplyChallanSearchRepository supplyChallanSearchRepository;

    public SupplyChallanExtendedService(SupplyChallanExtendedRepository supplyChallanExtendedRepository, SupplyChallanMapper supplyChallanMapper, SupplyChallanSearchRepository supplyChallanSearchRepository) {
        super(supplyChallanExtendedRepository, supplyChallanMapper, supplyChallanSearchRepository);
        this.supplyChallanExtendedRepository = supplyChallanExtendedRepository;
        this.supplyChallanMapper = supplyChallanMapper;
        this.supplyChallanSearchRepository = supplyChallanSearchRepository;
    }

    @Transactional
    public ByteArrayInputStream downloadChallan(Long id) throws Exception, DocumentException {

            List<DtTransaction> dtTransactions = new ArrayList<>();

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


            //document = createAuthorizationSection(document);

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
