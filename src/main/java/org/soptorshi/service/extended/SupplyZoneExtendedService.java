package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyZoneExtendedRepository;
import org.soptorshi.repository.search.SupplyZoneSearchRepository;
import org.soptorshi.service.SupplyZoneService;
import org.soptorshi.service.mapper.SupplyZoneMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyZone.
 */
@Service
@Transactional
public class SupplyZoneExtendedService extends SupplyZoneService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneExtendedService.class);

    public SupplyZoneExtendedService(SupplyZoneExtendedRepository supplyZoneExtendedRepository, SupplyZoneMapper supplyZoneMapper, SupplyZoneSearchRepository supplyZoneSearchRepository) {
        super(supplyZoneExtendedRepository, supplyZoneMapper, supplyZoneSearchRepository);
    }
}
