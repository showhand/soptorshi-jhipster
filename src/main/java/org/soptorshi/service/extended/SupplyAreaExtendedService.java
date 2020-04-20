package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyAreaExtendedRepository;
import org.soptorshi.repository.search.SupplyAreaSearchRepository;
import org.soptorshi.service.SupplyAreaService;
import org.soptorshi.service.mapper.SupplyAreaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyArea.
 */
@Service
@Transactional
public class SupplyAreaExtendedService extends SupplyAreaService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaExtendedService.class);

    public SupplyAreaExtendedService(SupplyAreaExtendedRepository supplyAreaExtendedRepository, SupplyAreaMapper supplyAreaMapper, SupplyAreaSearchRepository supplyAreaSearchRepository) {
        super(supplyAreaExtendedRepository, supplyAreaMapper, supplyAreaSearchRepository);
    }
}
