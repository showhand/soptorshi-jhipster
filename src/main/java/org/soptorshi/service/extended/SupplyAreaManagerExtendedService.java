package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyAreaManagerExtendedRepository;
import org.soptorshi.repository.search.SupplyAreaManagerSearchRepository;
import org.soptorshi.service.SupplyAreaManagerService;
import org.soptorshi.service.mapper.SupplyAreaManagerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyAreaManager.
 */
@Service
@Transactional
public class SupplyAreaManagerExtendedService extends SupplyAreaManagerService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaManagerExtendedService.class);

    public SupplyAreaManagerExtendedService(SupplyAreaManagerExtendedRepository supplyAreaManagerExtendedRepository, SupplyAreaManagerMapper supplyAreaManagerMapper, SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository) {
       super(supplyAreaManagerExtendedRepository, supplyAreaManagerMapper, supplyAreaManagerSearchRepository);
    }
}
