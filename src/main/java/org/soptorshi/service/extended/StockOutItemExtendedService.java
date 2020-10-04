package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.StockOutItemRepository;
import org.soptorshi.repository.search.StockOutItemSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.*;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.*;
import org.soptorshi.utils.SoptorshiUtils;
import org.soptorshi.web.rest.errors.InternalServerErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class StockOutItemExtendedService extends StockOutItemService {

    private final Logger log = LoggerFactory.getLogger(StockOutItemExtendedService.class);

    private final StockOutItemRepository stockOutItemRepository;

    private final StockOutItemMapper stockOutItemMapper;

    private final StockOutItemSearchRepository stockOutItemSearchRepository;

    private final StockStatusExtendedService stockStatusExtendedService;

    private final StockInItemExtendedService stockInItemExtendedService;

    private final ProductCategoryService productCategoryService;

    private final ProductService productService;

    private final InventoryLocationService inventoryLocationService;

    private final InventorySubLocationService inventorySubLocationService;

    private final ProductCategoryMapper productCategoryMapper;

    private final ProductMapper productMapper;

    private InventoryLocationMapper inventoryLocationMapper;

    private InventorySubLocationMapper inventorySubLocationMapper;

    private final StockStatusMapper stockStatusMapper;

    public StockOutItemExtendedService(StockOutItemRepository stockOutItemRepository, StockOutItemMapper stockOutItemMapper, StockOutItemSearchRepository stockOutItemSearchRepository,
                                       StockStatusExtendedService stockStatusExtendedService, StockInItemExtendedService stockInItemExtendedService,
                                       ProductCategoryService productCategoryService, ProductService productService, InventoryLocationService inventoryLocationService,
                                       InventorySubLocationService inventorySubLocationService, ProductCategoryMapper productCategoryMapper,
                                       ProductMapper productMapper, InventoryLocationMapper inventoryLocationMapper, InventorySubLocationMapper inventorySubLocationMapper,
                                       StockStatusMapper stockStatusMapper) {
        super(stockOutItemRepository, stockOutItemMapper, stockOutItemSearchRepository);

        this.stockOutItemRepository = stockOutItemRepository;
        this.stockOutItemMapper = stockOutItemMapper;
        this.stockOutItemSearchRepository = stockOutItemSearchRepository;
        this.stockStatusExtendedService = stockStatusExtendedService;
        this.stockInItemExtendedService = stockInItemExtendedService;
        this.productCategoryService = productCategoryService;
        this.productService = productService;
        this.inventoryLocationService = inventoryLocationService;
        this.inventorySubLocationService = inventorySubLocationService;
        this.productCategoryMapper = productCategoryMapper;
        this.productMapper = productMapper;
        this.inventoryLocationMapper = inventoryLocationMapper;
        this.inventorySubLocationMapper = inventorySubLocationMapper;
        this.stockStatusMapper = stockStatusMapper;
    }

    /**
     * Save a stockOutItem.
     *
     * @param stockOutItemDTO the entity to save
     * @return the persisted entity
     */

    public StockOutItemDTO save(StockOutItemDTO stockOutItemDTO) {
        log.debug("Request to save StockOutItem : {}", stockOutItemDTO);
        if (stockOutItemDTO.getId() == null && (stockOutItemDTO.getQuantity().compareTo(BigDecimal.ZERO) > 0)) {
            String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
            Instant currentDateTime = Instant.now();

            Optional<ProductCategoryDTO> productCategoryDTO = productCategoryService.findOne(stockOutItemDTO.getProductCategoriesId());
            Optional<ProductDTO> productDTO = productService.findOne(stockOutItemDTO.getProductsId());
            Optional<InventoryLocationDTO> inventoryLocationDTO = inventoryLocationService.findOne(stockOutItemDTO.getInventoryLocationsId());
            Optional<InventorySubLocationDTO> inventorySubLocationDTO = inventorySubLocationService.findOne(stockOutItemDTO.getInventorySubLocationsId());

            ProductCategory productCategory = productCategoryDTO.map(productCategoryMapper::toEntity).orElse(null);
            Product product = productDTO.map(productMapper::toEntity).orElse(null);
            InventoryLocation inventoryLocation = inventoryLocationDTO.map(inventoryLocationMapper::toEntity).orElse(null);
            InventorySubLocation inventorySubLocation = inventorySubLocationDTO.map(inventorySubLocationMapper::toEntity).orElse(null);

            StockInItem stockInItem = stockInItemExtendedService.getOne(
                productCategory,
                product,
                inventoryLocation,
                inventorySubLocation,
                stockOutItemDTO.getContainerTrackingId());

            StockStatus stockStatus = stockStatusExtendedService.getOne(
                productCategory,
                product,
                inventoryLocation,
                inventorySubLocation,
                stockOutItemDTO.getContainerTrackingId());

            if (stockInItem != null && stockStatus != null) {
                stockOutItemDTO.setStockOutBy(currentUser);
                stockOutItemDTO.setStockOutDate(currentDateTime);
                stockOutItemDTO.setStockInItemsId(stockInItem.getId());
                int response = updateStockStatus(stockOutItemDTO, stockStatus, currentUser, currentDateTime);
                if (response == 1) {
                    StockOutItem stockOutItem = stockOutItemMapper.toEntity(stockOutItemDTO);
                    stockOutItem = stockOutItemRepository.save(stockOutItem);
                    return stockOutItemMapper.toDto(stockOutItem);
                }
                throw new InternalServerErrorException("Item can not be stock out.");
            }
            throw new InternalServerErrorException("Illegal operation.");
        }
        throw new InternalServerErrorException("Only insert operation is available.");
    }

    private int updateStockStatus(StockOutItemDTO stockOutItemDTO,
                                  StockStatus stockStatus, String currentUser, Instant currentDateTime) {
        if (stockStatus != null) {
            if (stockOutItemDTO.getQuantity().compareTo(stockStatus.getAvailableQuantity()) > 0) {
                throw new InternalServerErrorException("Required quantity is greater than available quantity");
            } else {
                stockStatus.setAvailableQuantity(stockStatus.getAvailableQuantity().subtract(stockOutItemDTO.getQuantity()));
                stockStatus.setAvailablePrice(stockStatus.getAvailablePrice().subtract((stockOutItemDTO.getQuantity().multiply(stockStatus.getStockInItem().getStockInProcesses().getUnitPrice()))));
                stockStatusExtendedService.save(stockStatusMapper.toDto(stockStatus));
            }
            return 1;
        }
        return  0;
    }




    public static Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

    static Font TIME_ROMAN_11 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    static Font TIMES_BOLD_11 = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    static Font TIME_ROMAN_12 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    static Font TIME_BOLD_12 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    static Font TIMES_BOLD_14 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);

    public ByteArrayInputStream getChallan(Long id) throws DocumentException {

        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 40, 40);
        document.addTitle("Stock Out Challan");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new HolidayTypeReportService.headerAndFooter());
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;

        paragraph = new Paragraph(new Chunk("Seven Oceans Fish Processing Ltd", SoptorshiUtils.mBigBoldFont));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(new Chunk("Stock Out Challan", TIMES_BOLD_11));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        Optional<StockOutItemDTO> stockOutItem = this.findOne(id);

        if(stockOutItem.isPresent()) {
            pdfPTable = new PdfPTable(2);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setTotalWidth(new float[]{30, 70});

            pdfPCell = new PdfPCell(new Paragraph("Stock Out Id", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getId() + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Product Category", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getProductCategoriesName() + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Product", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getProductsName() + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Inventory Location", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getInventoryLocationsName() + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Inventory Sub Location", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getInventorySubLocationsName() + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Container Tracking Id", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getContainerTrackingId() + "", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Quantity", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getQuantity() + " ", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Receiver", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getReceiverId() == null ? "" : stockOutItem.get().getReceiverId() + " ", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Receiving Palace", TIMES_BOLD_11));
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(stockOutItem.get().getReceivingPlace() == null ? "" : stockOutItem.get().getReceivingPlace() + " ", TIME_ROMAN_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            //pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            document.add(pdfPTable);
        }
        else {
            paragraph = new Paragraph(new Chunk("No records found for stock out id: ." + id, TIME_ROMAN_11));
            document.add(paragraph);
        }

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    void lineBreak(Paragraph p, int number) {
        for(int i = 0; i < number; i++) {
            p.add(new Paragraph(" "));
        }
    }
}
