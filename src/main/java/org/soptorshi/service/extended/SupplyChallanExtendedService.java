package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyChallanExtendedRepository;
import org.soptorshi.repository.search.SupplyChallanSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.service.SupplyChallanService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.SupplyChallanMapper;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
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

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    private final SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService;

    private final SupplyShopExtendedService supplyShopExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    private final SupplyOrderMapper supplyOrderMapper;

    public SupplyChallanExtendedService(SupplyChallanExtendedRepository supplyChallanExtendedRepository, SupplyChallanMapper supplyChallanMapper, SupplyChallanSearchRepository supplyChallanSearchRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService, SupplyShopExtendedService supplyShopExtendedService, EmployeeExtendedRepository employeeExtendedRepository, SupplyAreaExtendedService supplyAreaExtendedService, SupplyOrderExtendedService supplyOrderExtendedService, SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService, SupplyOrderMapper supplyOrderMapper) {
        super(supplyChallanExtendedRepository, supplyChallanMapper, supplyChallanSearchRepository);
        this.supplyChallanExtendedRepository = supplyChallanExtendedRepository;
        this.supplyChallanMapper = supplyChallanMapper;
        this.supplyChallanSearchRepository = supplyChallanSearchRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
        this.supplySalesRepresentativeExtendedService = supplySalesRepresentativeExtendedService;
        this.supplyShopExtendedService = supplyShopExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.supplyOrderExtendedService = supplyOrderExtendedService;
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
        this.supplyOrderMapper = supplyOrderMapper;
    }

    public SupplyChallanDTO save(SupplyChallanDTO supplyChallanDTO) {
        log.debug("Request to save SupplyChallan : {}", supplyChallanDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (supplyChallanDTO.getId() == null) {
            supplyChallanDTO.setCreatedBy(currentUser);
            supplyChallanDTO.setCreatedOn(currentDateTime);
        } else {
            supplyChallanDTO.setUpdatedBy(currentUser);
            supplyChallanDTO.setUpdatedOn(currentDateTime);
        }
        SupplyChallan supplyChallan = supplyChallanMapper.toEntity(supplyChallanDTO);
        supplyChallan = supplyChallanExtendedRepository.save(supplyChallan);
        SupplyChallanDTO result = supplyChallanMapper.toDto(supplyChallan);
        //supplyChallanSearchRepository.save(supplyChallan);
        return result;
    }

    public boolean isValidInput(SupplyChallanDTO supplyChallanDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyChallanDTO, currentUser) && isValidZoneManager(supplyChallanDTO) && isValidArea(supplyChallanDTO) && isValidAreaManager(supplyChallanDTO) && isValidSalesRepresentative(supplyChallanDTO) && isValidShop(supplyChallanDTO) && isValidStatus(supplyChallanDTO);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            return isValidZoneAsPerAreaManagerRole(supplyChallanDTO, currentUser) && isValidZoneManager(supplyChallanDTO) && isValidArea(supplyChallanDTO) && isValidAreaManager(supplyChallanDTO) && isValidSalesRepresentative(supplyChallanDTO) && isValidShop(supplyChallanDTO) && isValidStatus(supplyChallanDTO);
        }
        return isValidZoneManager(supplyChallanDTO) && isValidArea(supplyChallanDTO) && isValidAreaManager(supplyChallanDTO) && isValidSalesRepresentative(supplyChallanDTO) && isValidShop(supplyChallanDTO) && isValidStatus(supplyChallanDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyChallanDTO supplyChallanDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyChallanDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneAsPerAreaManagerRole(SupplyChallanDTO supplyChallanDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get(), SupplyAreaManagerStatus.ACTIVE);
            for (SupplyAreaManager supplyAreaManager : supplyAreaManagers) {
                if (supplyAreaManager.getSupplyZone().getId().equals(supplyChallanDTO.getSupplyZoneId())
                    && supplyAreaManager.getSupplyZoneManager().getId().equals(supplyChallanDTO.getSupplyZoneManagerId())
                    && supplyAreaManager.getSupplyArea().getId().equals(supplyChallanDTO.getSupplyAreaId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyChallanDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }

    private boolean isValidArea(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyChallanDTO.getSupplyAreaId());
        return selectedArea.map(supplyAreaDTO -> supplyAreaDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId())).orElse(false);
    }

    private boolean isValidAreaManager(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplyChallanDTO.getSupplyAreaManagerId());
        return selectedAreaManager.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyChallanDTO.getSupplyAreaId())).isPresent();
    }

    private boolean isValidSalesRepresentative(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplySalesRepresentativeDTO> selectedSalesRepresentative = supplySalesRepresentativeExtendedService.findOne(supplyChallanDTO.getSupplySalesRepresentativeId());
        return selectedSalesRepresentative.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyChallanDTO.getSupplyAreaId()) &&
            supplyAreaManagerDTO.getSupplyAreaManagerId().equals(supplyChallanDTO.getSupplyAreaManagerId())).isPresent();
    }

    private boolean isValidShop(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyShopDTO> selectedSupplyShop = supplyShopExtendedService.findOne(supplyChallanDTO.getSupplyShopId());
        return selectedSupplyShop.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyChallanDTO.getSupplyAreaId()) &&
            supplyAreaManagerDTO.getSupplyAreaManagerId().equals(supplyChallanDTO.getSupplyAreaManagerId()) &&
            supplyAreaManagerDTO.getSupplySalesRepresentativeId().equals(supplyChallanDTO.getSupplySalesRepresentativeId())).isPresent();
    }

    public boolean isValidStatus(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyOrderDTO> supplyOrderDTO1 = supplyOrderExtendedService.findOne(supplyChallanDTO.getSupplyOrderId());
        return supplyOrderDTO1.map(orderDTO -> orderDTO.getStatus().equals(SupplyOrderStatus.ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION)).orElse(false);
    }

    @Transactional
    public ByteArrayInputStream downloadChallan(Long id) throws Exception, DocumentException {

        Optional<SupplyChallanDTO> supplyChallanDTO = findOne(id);
        if (supplyChallanDTO.isPresent()) {

            Optional<SupplyOrderDTO> supplyOrderDTO = supplyOrderExtendedService.findOne(supplyChallanDTO.get().getSupplyOrderId());

            if (supplyChallanDTO.isPresent()) {

                SupplyOrder supplyOrder = supplyOrderMapper.toEntity(supplyOrderDTO.get());

                List<SupplyOrderDetails> supplyOrderDetails = supplyOrderDetailsExtendedService.getAllBySupplyOrder(supplyOrder);

                Document document = new Document();
                document.setPageSize(PageSize.A4.rotate());
                document.addTitle("Challan");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                document.open();

                Paragraph paragraph = new Paragraph("7 Oceans Ltd.", SoptorshiUtils.mBigBoldFont);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.add(Chunk.NEWLINE);

                paragraph = new Paragraph("Order No: " + supplyOrder.getOrderNo(), SoptorshiUtils.mBigLiteFont);
                paragraph.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph);
                document.add(Chunk.NEWLINE);


                PdfPTable table = new PdfPTable(4); // 3 columns.

                PdfPCell cell = new PdfPCell(new Paragraph("Product Category"));
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("Product Name"));
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("Quantity"));
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("Price"));
                table.addCell(cell);

                for(SupplyOrderDetails supplyOrderDetails1: supplyOrderDetails) {
                     cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getProductCategory().getName()));
                    table.addCell(cell);
                     cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getProduct().getName()));
                    table.addCell(cell);
                     cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getQuantity().toString()));
                    table.addCell(cell);
                     cell = new PdfPCell(new Paragraph(supplyOrderDetails1.getPrice().toString()));
                    table.addCell(cell);
                }

                document.add(table);


                document = createAuthorizationSection(document);

                document.close();
                return new ByteArrayInputStream(baos.toByteArray());
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
