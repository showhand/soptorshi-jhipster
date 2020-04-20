package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyShopExtendedRepository;
import org.soptorshi.repository.search.SupplyShopSearchRepository;
import org.soptorshi.service.SupplyShopService;
import org.soptorshi.service.mapper.SupplyShopMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyShop.
 */
@Service
@Transactional
public class SupplyShopExtendedService extends SupplyShopService {

    private final Logger log = LoggerFactory.getLogger(SupplyShopExtendedService.class);

    public SupplyShopExtendedService(SupplyShopExtendedRepository supplyShopExtendedRepository, SupplyShopMapper supplyShopMapper, SupplyShopSearchRepository supplyShopSearchRepository) {
        super(supplyShopExtendedRepository, supplyShopMapper, supplyShopSearchRepository);
    }
}
