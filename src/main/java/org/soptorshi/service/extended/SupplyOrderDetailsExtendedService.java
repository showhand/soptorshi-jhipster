package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.repository.extended.SupplyOrderDetailsExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderDetailsSearchRepository;
import org.soptorshi.service.SupplyOrderDetailsService;
import org.soptorshi.service.mapper.SupplyOrderDetailsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyOrderDetails.
 */
@Service
@Transactional
public class SupplyOrderDetailsExtendedService extends SupplyOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsExtendedService.class);

    private SupplyOrderDetailsExtendedRepository supplyOrderDetailsExtendedRepository;

    public SupplyOrderDetailsExtendedService(SupplyOrderDetailsExtendedRepository supplyOrderDetailsExtendedRepository, SupplyOrderDetailsMapper supplyOrderDetailsMapper, SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository) {
       super(supplyOrderDetailsExtendedRepository, supplyOrderDetailsMapper, supplyOrderDetailsSearchRepository);
       this.supplyOrderDetailsExtendedRepository = supplyOrderDetailsExtendedRepository;
    }

    Optional<List<SupplyOrderDetails>> getAllBySupplyOrder(SupplyOrder supplyOrder) {
        return supplyOrderDetailsExtendedRepository.getAllBySupplyOrder(supplyOrder);
    }
}
