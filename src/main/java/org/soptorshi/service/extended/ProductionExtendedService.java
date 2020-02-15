package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.ProductionRepository;
import org.soptorshi.repository.search.ProductionSearchRepository;
import org.soptorshi.service.ProductionService;
import org.soptorshi.service.mapper.ProductionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Production.
 */
@Service
@Transactional
public class ProductionExtendedService extends ProductionService {

    private final Logger log = LoggerFactory.getLogger(ProductionExtendedService.class);

    public ProductionExtendedService(ProductionRepository productionRepository, ProductionMapper productionMapper, ProductionSearchRepository productionSearchRepository) {
        super(productionRepository, productionMapper, productionSearchRepository);
    }
}
