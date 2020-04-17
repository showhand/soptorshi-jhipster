package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyOrderExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.service.SupplyOrderService;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyOrder.
 */
@Service
@Transactional
public class SupplyOrderExtendedService extends SupplyOrderService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderExtendedService.class);

    public SupplyOrderExtendedService(SupplyOrderExtendedRepository supplyOrderExtendedRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository) {
        super(supplyOrderExtendedRepository, supplyOrderMapper, supplyOrderSearchRepository);
    }
}
