package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.repository.extended.SupplyChallanExtendedRepository;
import org.soptorshi.repository.search.SupplyChallanSearchRepository;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.service.SupplyChallanService;
import org.soptorshi.service.dto.SupplyChallanDTO;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.mapper.SupplyChallanMapper;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

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

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    private final SupplyOrderMapper supplyOrderMapper;

    public SupplyChallanExtendedService(SupplyChallanExtendedRepository supplyChallanExtendedRepository, SupplyChallanMapper supplyChallanMapper, SupplyChallanSearchRepository supplyChallanSearchRepository, SupplyOrderExtendedService supplyOrderExtendedService, SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService, SupplyOrderMapper supplyOrderMapper) {
        super(supplyChallanExtendedRepository, supplyChallanMapper, supplyChallanSearchRepository);
        this.supplyChallanExtendedRepository = supplyChallanExtendedRepository;
        this.supplyChallanMapper = supplyChallanMapper;
        this.supplyChallanSearchRepository = supplyChallanSearchRepository;
        this.supplyOrderExtendedService = supplyOrderExtendedService;
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
        this.supplyOrderMapper = supplyOrderMapper;
    }

    @Transactional
    public ByteArrayInputStream downloadChallan(Long id) throws Exception, DocumentException {

        Optional<SupplyChallanDTO> supplyChallanDTO = this.findOne(id);
        if(supplyChallanDTO.isPresent()) {

            Optional<SupplyOrderDTO> supplyOrderDTO = supplyOrderExtendedService.findOne(supplyChallanDTO.get().getSupplyOrderId());

            if (supplyOrderDTO.isPresent()) {

                SupplyOrder supplyOrder = supplyOrderMapper.toEntity(supplyOrderDTO.get());

                Optional<List<SupplyOrderDetails>> supplyOrderDetails = supplyOrderDetailsExtendedService.getAllBySupplyOrder(supplyOrder);

                if (supplyOrderDetails.isPresent()) {

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

                    paragraph = new Paragraph("Order No: " + supplyOrder.getOrderNo(), SoptorshiUtils.mBigLiteFont);
                    paragraph.setAlignment(Element.ALIGN_LEFT);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE);




                    document = createAuthorizationSection(document);

                    document.close();
                    return new ByteArrayInputStream(baos.toByteArray());
                }
            }
        }
        throw new BadRequestAlertException("Challan not found", "supply-challan", "idnull");
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
