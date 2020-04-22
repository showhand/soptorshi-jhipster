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
import org.soptorshi.repository.extended.SupplyOrderExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.service.SupplyOrderService;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SupplyOrder.
 */
@Service
@Transactional
public class SupplyOrderExtendedService extends SupplyOrderService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderExtendedService.class);

    private final SupplyOrderExtendedRepository supplyOrderExtendedRepository;

    private final SupplyOrderMapper supplyOrderMapper;

    private final SupplyOrderSearchRepository supplyOrderSearchRepository;

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    public SupplyOrderExtendedService(SupplyOrderExtendedRepository supplyOrderExtendedRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository, SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService) {
        super(supplyOrderExtendedRepository, supplyOrderMapper, supplyOrderSearchRepository);
        this.supplyOrderExtendedRepository = supplyOrderExtendedRepository;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyOrderSearchRepository = supplyOrderSearchRepository;
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
    }


    /**
     * Save a supplyOrder.
     *
     * @param supplyOrderDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public SupplyOrderDTO save(SupplyOrderDTO supplyOrderDTO) {
        log.debug("Request to save SupplyOrder : {}", supplyOrderDTO);

        SupplyOrder supplyOrder = supplyOrderMapper.toEntity(supplyOrderDTO);


        if (supplyOrderDTO.getId() != null) {
            Optional<List<SupplyOrderDetails>> supplyOrderDetails = supplyOrderDetailsExtendedService.getAllBySupplyOrder(supplyOrder);

            if (supplyOrderDetails.isPresent()) {
                BigDecimal total = BigDecimal.ZERO;

                for (SupplyOrderDetails supplyOrderDetail : supplyOrderDetails.get()) {
                    total = total.add(supplyOrderDetail.getOfferedPrice());
                }

                supplyOrder.setOfferAmount(total);
            }
        }


        supplyOrder = supplyOrderExtendedRepository.save(supplyOrder);
        SupplyOrderDTO result = supplyOrderMapper.toDto(supplyOrder);
        supplyOrderSearchRepository.save(supplyOrder);

        return result;
    }

    public List<LocalDate> getAllDistinctSupplyOrderDate() {
        log.debug("Request to get all Distinct Supply Order Date");
        List<LocalDate> dates = new ArrayList<>();
        List<SupplyOrder> supplyOrders = supplyOrderExtendedRepository.findAll();
        for (SupplyOrder supplyOrder : supplyOrders) {
            dates.add(supplyOrder.getDateOfOrder());
        }
        return dates.stream().distinct().collect(Collectors.toList());
    }

    public Long updateReferenceNoAfterFilterByDate(String referenceNo, LocalDate fromDate, LocalDate toDate,
                                                   SupplyOrderStatus status) {
        Optional<List<SupplyOrder>> supplyOrders = supplyOrderExtendedRepository.getByDateOfOrderGreaterThanEqualAndDateOfOrderLessThanEqualAndSupplyOrderStatus(fromDate, toDate,
            status);

        if (supplyOrders.isPresent()) {
            for (SupplyOrder supplyOrder : supplyOrders.get()) {
                supplyOrder.setAccumulationReferenceNo(referenceNo);
                supplyOrder.setSupplyOrderStatus(SupplyOrderStatus.PROCESSING_ORDER);
                SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(supplyOrder);
                save(supplyOrderDTO);
            }
            return 1L;
        } else {
            return 0L;
        }
    }


    @Transactional
    public ByteArrayInputStream downloadAccumulatedOrders(String refNo) throws Exception, DocumentException {

        Map<SupplyOrder, List<SupplyOrderDetails>> map = new HashMap<>();

        Optional<List<SupplyOrder>> supplyOrders = supplyOrderExtendedRepository.getByAccumulationReferenceNo(refNo);

        if (supplyOrders.isPresent()) {

            for (SupplyOrder supplyOrder : supplyOrders.get()) {
                Optional<List<SupplyOrderDetails>> supplyOrderDetails = supplyOrderDetailsExtendedService.getAllBySupplyOrder(supplyOrder);
                supplyOrderDetails.ifPresent(orderDetails -> map.put(supplyOrder, orderDetails));
            }

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

            for(Map.Entry<SupplyOrder, List<SupplyOrderDetails>> map1: map.entrySet()) {

                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                table.setTotalWidth(new float[]{15, 15, 15, 15, 15, 15});

                PdfPCell cell = new PdfPCell();

                cell.addElement(new Paragraph(new Paragraph("Order No", SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph(map1.getKey().getOrderNo(), SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph("Area Name", SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph(map1.getKey().getSupplyArea().getAreaName(), SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph("Zone Name", SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph(map1.getKey().getSupplyZone().getZoneName(), SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.setTotalWidth(new float[]{40, 30, 30});

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph("Product", SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph("Quantity", SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph("Price", SoptorshiUtils.mBigLiteFont)));
                table.addCell(cell);

                for(SupplyOrderDetails supplyOrderDetails: map1.getValue()) {
                    cell = new PdfPCell();
                    cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getProduct().getName(), SoptorshiUtils.mBigLiteFont)));
                    table.addCell(cell);

                    cell = new PdfPCell();
                    cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getQuantity() + "", SoptorshiUtils.mBigLiteFont)));
                    table.addCell(cell);

                    cell = new PdfPCell();
                    cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getOfferedPrice() + "", SoptorshiUtils.mBigLiteFont)));
                    table.addCell(cell);
                }

                document.add(table);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
            }

            document.add(new Paragraph("Accumulated Order", SoptorshiUtils.mBigLiteFont));
            document.add(Chunk.NEWLINE);


            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setTotalWidth(new float[]{40, 30, 30});

            PdfPCell cell = new PdfPCell();
            cell.addElement(new Paragraph(new Paragraph("Product", SoptorshiUtils.mBigLiteFont)));
            table.addCell(cell);

            cell = new PdfPCell();
            cell.addElement(new Paragraph(new Paragraph("Quantity", SoptorshiUtils.mBigLiteFont)));
            table.addCell(cell);

            cell = new PdfPCell();
            cell.addElement(new Paragraph(new Paragraph("Price", SoptorshiUtils.mBigLiteFont)));
            table.addCell(cell);

            for(Map.Entry<SupplyOrder, List<SupplyOrderDetails>> map1: map.entrySet()) {
                for (SupplyOrderDetails supplyOrderDetails : map1.getValue()) {

                    cell = new PdfPCell();
                    cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getProduct().getName(), SoptorshiUtils.mBigLiteFont)));
                    table.addCell(cell);

                    cell = new PdfPCell();
                    cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getQuantity() + "", SoptorshiUtils.mBigLiteFont)));
                    table.addCell(cell);

                    cell = new PdfPCell();
                    cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getOfferedPrice() + "", SoptorshiUtils.mBigLiteFont)));
                    table.addCell(cell);
                }
            }


            /*document = createAuthorizationSection(document);*/

            document.close();
            return new ByteArrayInputStream(baos.toByteArray());
        }

        throw new BadRequestAlertException("Order not found", "supply-orders", "idnull");
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
