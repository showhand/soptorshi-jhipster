package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyZoneManagerExtendedRepository;
import org.soptorshi.repository.search.SupplyZoneManagerSearchRepository;
import org.soptorshi.service.SupplyZoneManagerService;
import org.soptorshi.service.mapper.SupplyZoneManagerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyZoneManager.
 */
@Service
@Transactional
public class SupplyZoneManagerExtendedService extends SupplyZoneManagerService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneManagerExtendedService.class);

    public SupplyZoneManagerExtendedService(SupplyZoneManagerExtendedRepository supplyZoneManagerExtendedRepository, SupplyZoneManagerMapper supplyZoneManagerMapper, SupplyZoneManagerSearchRepository supplyZoneManagerSearchRepository) {
        super(supplyZoneManagerExtendedRepository, supplyZoneManagerMapper, supplyZoneManagerSearchRepository);
    }
}
