package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyOrderDetailsExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderDetailsSearchRepository;
import org.soptorshi.service.SupplyOrderDetailsService;
import org.soptorshi.service.mapper.SupplyOrderDetailsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyOrderDetails.
 */
@Service
@Transactional
public class SupplyOrderDetailsExtendedService extends SupplyOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsExtendedService.class);

    public SupplyOrderDetailsExtendedService(SupplyOrderDetailsExtendedRepository supplyOrderDetailsExtendedRepository, SupplyOrderDetailsMapper supplyOrderDetailsMapper, SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository) {
       super(supplyOrderDetailsExtendedRepository, supplyOrderDetailsMapper, supplyOrderDetailsSearchRepository);
    }
}
