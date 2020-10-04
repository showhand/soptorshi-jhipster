package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Production;
import org.soptorshi.repository.extended.ProductionExtendedRepository;
import org.soptorshi.repository.search.ProductionSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.ProductionService;
import org.soptorshi.service.dto.ProductionDTO;
import org.soptorshi.service.mapper.ProductionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing Production.
 */
@Service
@Transactional
public class ProductionExtendedService extends ProductionService {

    private final Logger log = LoggerFactory.getLogger(ProductionExtendedService.class);

    private final ProductionExtendedRepository productionExtendedRepository;

    private final ProductionMapper productionMapper;

    private final ProductionSearchRepository productionSearchRepository;

    private final PurchaseOrderExtendedService purchaseOrderExtendedService;

    public ProductionExtendedService(ProductionExtendedRepository productionExtendedRepository, ProductionMapper productionMapper, ProductionSearchRepository productionSearchRepository, PurchaseOrderExtendedService purchaseOrderExtendedService) {
        super(productionExtendedRepository, productionMapper, productionSearchRepository);
        this.productionExtendedRepository = productionExtendedRepository;
        this.productionMapper = productionMapper;
        this.productionSearchRepository = productionSearchRepository;
        this.purchaseOrderExtendedService = purchaseOrderExtendedService;
    }

    public ProductionDTO save(ProductionDTO productionDTO) {
        log.debug("Request to save Production : {}", productionDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
            SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (productionDTO.getId() == null) {
            productionDTO.setCreatedBy(currentUser);
            productionDTO.setCreatedOn(currentDateTime);
        } else {
            productionDTO.setUpdatedBy(currentUser);
            productionDTO.setUpdatedOn(currentDateTime);
        }

        Production production = productionMapper.toEntity(productionDTO);
        production = productionExtendedRepository.save(production);
        ProductionDTO result = productionMapper.toDto(production);
        productionSearchRepository.save(production);
        return result;
    }
}
