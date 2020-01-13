package org.soptorshi.service.extended;

import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.QuotationDetails;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.enumeration.ProductType;
import org.soptorshi.domain.enumeration.ProductionWeightStep;
import org.soptorshi.domain.enumeration.PurchaseOrderStatus;
import org.soptorshi.domain.enumeration.SelectionType;
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.extended.QuotationDetailsExtendedRepository;
import org.soptorshi.repository.extended.QuotationExtendedRepository;
import org.soptorshi.repository.search.PurchaseOrderSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.ProductService;
import org.soptorshi.service.ProductionService;
import org.soptorshi.service.PurchaseOrderService;
import org.soptorshi.service.dto.ProductionDTO;
import org.soptorshi.service.dto.PurchaseOrderDTO;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.dto.StockInProcessDTO;
import org.soptorshi.service.mapper.PurchaseOrderMapper;
import org.soptorshi.service.mapper.RequisitionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseOrderExtendedService extends PurchaseOrderService {

    private final RequisitionExtendedService requisitionExtendedService;

    private final RequisitionMapper requisitionMapper;

    private final QuotationExtendedService quotationExtendedService;

    private final QuotationExtendedRepository quotationExtendedRepository;

    private final QuotationDetailsExtendedService quotationDetailsExtendedService;

    private final QuotationDetailsExtendedRepository quotationDetailsExtendedRepository;

    private final StockInProcessExtendedService stockInProcessExtendedService;

    private final ProductService productService;

    private final ProductionService productionService;

    public PurchaseOrderExtendedService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper, PurchaseOrderSearchRepository purchaseOrderSearchRepository, RequisitionExtendedService requisitionExtendedService,
                                        RequisitionMapper requisitionMapper,
                                        QuotationExtendedService quotationExtendedService,
                                        QuotationExtendedRepository quotationExtendedRepository,
                                        QuotationDetailsExtendedService quotationDetailsExtendedService,
                                        QuotationDetailsExtendedRepository quotationDetailsExtendedRepository,
                                        StockInProcessExtendedService stockInProcessExtendedService,
                                        ProductService productService,
                                        ProductionService productionService) {
        super(purchaseOrderRepository, purchaseOrderMapper, purchaseOrderSearchRepository);
        this.requisitionExtendedService = requisitionExtendedService;
        this.requisitionMapper = requisitionMapper;
        this.quotationExtendedService = quotationExtendedService;
        this.quotationExtendedRepository = quotationExtendedRepository;
        this.quotationDetailsExtendedService = quotationDetailsExtendedService;
        this.quotationDetailsExtendedRepository = quotationDetailsExtendedRepository;
        this.stockInProcessExtendedService = stockInProcessExtendedService;
        this.productService = productService;
        this.productionService = productionService;
    }

    @Override
    @Transactional
    public PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO) {
        purchaseOrderDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        purchaseOrderDTO.setModifiedOn(LocalDate.now());

        if(purchaseOrderDTO.getStatus() != null && purchaseOrderDTO.getStatus().equals(PurchaseOrderStatus.APPROVED_BY_CFO)) {
            Optional<RequisitionDTO> requisitionDTO = requisitionExtendedService.findOne(purchaseOrderDTO.getRequisitionId());
            if (requisitionDTO.isPresent()) {
                Requisition requisition = requisitionMapper.toEntity(requisitionDTO.get());
                Optional<Quotation> quotation = quotationExtendedRepository.findByRequisitionAndAndSelectionStatus(requisition, SelectionType.SELECTED);
                if (quotation.isPresent()) {
                    List<QuotationDetails> quotationDetails = new ArrayList<>();
                    quotationDetails = quotationDetailsExtendedRepository.findByQuotationId(quotation.get().getId());
                    insertInStockInProcess(quotation.get(), quotationDetails);
                    return super.save(purchaseOrderDTO);
                }
            }
        }
        return super.save(purchaseOrderDTO);
    }

    private void insertInStockInProcess(Quotation quotation, List<QuotationDetails> quotationDetails) {
        for(QuotationDetails quotationDetails1: quotationDetails) {
            StockInProcessDTO stockInProcessDTO = getStockInProcess(quotation, quotationDetails1);
            stockInProcessExtendedService.save(stockInProcessDTO);
        }
    }

    private void insertInProduction(Quotation quotation, List<QuotationDetails> quotationDetails) {
        for(QuotationDetails quotationDetails1: quotationDetails) {
            ProductionDTO productionDTO = getProduction(quotation, quotationDetails1);
            productionService.save(productionDTO);
        }
    }

    private StockInProcessDTO getStockInProcess(Quotation quotation, QuotationDetails quotationDetails) {
        StockInProcessDTO stockInProcessDTO = new StockInProcessDTO();
        stockInProcessDTO.setProductCategoriesId(quotationDetails.getProduct().getProductCategory().getId());
        stockInProcessDTO.setProductsId(quotationDetails.getProduct().getId());
        stockInProcessDTO.setTotalQuantity(BigDecimal.valueOf(quotationDetails.getQuantity()));
        stockInProcessDTO.setUnit(quotationDetails.getUnitOfMeasurements());
        stockInProcessDTO.setUnitPrice(quotationDetails.getRate());
        stockInProcessDTO.setTypeOfProduct(ProductType.FINISHED_PRODUCT);
        stockInProcessDTO.setRequisitionsId(quotation.getRequisition().getId());
        return stockInProcessDTO;
    }

    private ProductionDTO getProduction(Quotation quotation, QuotationDetails quotationDetails) {
        ProductionDTO productionDTO = new ProductionDTO();
        productionDTO.setProductCategoriesId(quotationDetails.getProduct().getProductCategory().getId());
        productionDTO.setProductsId(quotationDetails.getProduct().getId());
        productionDTO.setQuantity(BigDecimal.valueOf(quotationDetails.getQuantity()));
        productionDTO.setUnit(quotationDetails.getUnitOfMeasurements());
        productionDTO.setWeightStep(ProductionWeightStep.RAW);
        productionDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
        productionDTO.setCreatedOn(Instant.now());
        return productionDTO;
    }
}
