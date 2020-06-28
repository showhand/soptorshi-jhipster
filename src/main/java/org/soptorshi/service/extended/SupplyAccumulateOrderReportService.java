package org.soptorshi.service.extended;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SupplyAccumulateOrderReportService {

    /*private final Logger log = LoggerFactory.getLogger(SupplyAccumulateOrderReportService.class);

    private final SupplyOrderExtendedRepository supplyOrderExtendedRepository;

    private final SupplyOrderMapper supplyOrderMapper;

    private final SupplyOrderSearchRepository supplyOrderSearchRepository;

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    public SupplyAccumulateOrderReportService(SupplyOrderExtendedRepository supplyOrderExtendedRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository, SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService) {
        this.supplyOrderExtendedRepository = supplyOrderExtendedRepository;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyOrderSearchRepository = supplyOrderSearchRepository;
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
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

                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.setTotalWidth(new float[]{40, 30, 30});

                PdfPCell cell = new PdfPCell();

                cell.addElement(new Paragraph(new Paragraph("Order No: " + map1.getKey().getOrderNo(), SoptorshiUtils.mBigLiteFont)));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                *//*cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph(map1.getKey().getOrderNo(), SoptorshiUtils.mBigLiteFont)));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);*//*

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph("Area: " + map1.getKey().getSupplyArea().getName(), SoptorshiUtils.mBigLiteFont)));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                *//*cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph(map1.getKey().getSupplyArea().getAreaName(), SoptorshiUtils.mBigLiteFont)));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);*//*

                cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph("Zone: " + map1.getKey().getSupplyZone().getName(), SoptorshiUtils.mBigLiteFont)));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                *//*cell = new PdfPCell();
                cell.addElement(new Paragraph(new Paragraph(map1.getKey().getSupplyZone().getZoneName(), SoptorshiUtils.mBigLiteFont)));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);*//*

                document.add(table);
                document.add(Chunk.NEWLINE);

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
                    cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getPrice() + "", SoptorshiUtils.mBigLiteFont)));
                    table.addCell(cell);
                }

                document.add(table);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
            }

            document.add(new Paragraph("Accumulated Order", SoptorshiUtils.mBigLiteFont));
            document.add(Chunk.NEWLINE);


//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            table.setTotalWidth(new float[]{40, 30, 30});
//
//            List<SupplyOrderDetails> supplyOrderDetails1 = new ArrayList<>();
//
//            for(Map.Entry<SupplyOrder, List<SupplyOrderDetails>> map1: map.entrySet()) {
//                for (SupplyOrderDetails supplyOrderDetails : map1.getValue()) {
//
//                    boolean flag = false;
//
//                    for(SupplyOrderDetails supplyOrderDetails2: supplyOrderDetails1) {
//                        if(supplyOrderDetails2.getProduct().getId().equals(supplyOrderDetails.getProduct().getId())) {
//                            flag = true;
//                            BigDecimal quantity = supplyOrderDetails2.getQuantity();
//                            BigDecimal price = supplyOrderDetails2.getOfferedPrice();
//                            supplyOrderDetails2.setQuantity(quantity.add(supplyOrderDetails.getQuantity()));
//                            supplyOrderDetails2.setOfferedPrice(quantity.add(s upplyOrderDetails.getOfferedPrice()));
//                        }
//                    }
//                    if(flag) {
//                        flag = false;
//                    }
//                    else {
//                        supplyOrderDetails1.add(supplyOrderDetails);
//                    }
//                }
//            }
//
//            PdfPCell cell = new PdfPCell();
//            cell.addElement(new Paragraph(new Paragraph("Product", SoptorshiUtils.mBigLiteFont)));
//            table.addCell(cell);
//
//            cell = new PdfPCell();
//            cell.addElement(new Paragraph(new Paragraph("Quantity", SoptorshiUtils.mBigLiteFont)));
//            table.addCell(cell);
//
//            cell = new PdfPCell();
//            cell.addElement(new Paragraph(new Paragraph("Price", SoptorshiUtils.mBigLiteFont)));
//            table.addCell(cell);
//
//            for(SupplyOrderDetails supplyOrderDetails: supplyOrderDetails1) {
//
//                cell = new PdfPCell();
//                cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getProduct().getName(), SoptorshiUtils.mBigLiteFont)));
//                table.addCell(cell);
//
//                cell = new PdfPCell();
//                cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getQuantity() + "", SoptorshiUtils.mBigLiteFont)));
//                table.addCell(cell);
//
//                cell = new PdfPCell();
//                cell.addElement(new Paragraph(new Paragraph(supplyOrderDetails.getOfferedPrice() + "", SoptorshiUtils.mBigLiteFont)));
//                table.addCell(cell);
//            }
//
//            document.add(table);


            *//*document = createAuthorizationSection(document);*//*

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
    }*/
}
