package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplyChallanExtendedRepository;
import org.soptorshi.repository.search.SupplyChallanSearchRepository;
import org.soptorshi.service.SupplyChallanService;
import org.soptorshi.service.mapper.SupplyChallanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplyChallan.
 */
@Service
@Transactional
public class SupplyChallanExtendedService extends SupplyChallanService {

    private final Logger log = LoggerFactory.getLogger(SupplyChallanExtendedService.class);

    private final SupplyChallanExtendedRepository supplyChallanExtendedRepository;

    private final SupplyChallanMapper supplyChallanMapper;

    private final SupplyChallanSearchRepository supplyChallanSearchRepository;

    public SupplyChallanExtendedService(SupplyChallanExtendedRepository supplyChallanExtendedRepository, SupplyChallanMapper supplyChallanMapper, SupplyChallanSearchRepository supplyChallanSearchRepository) {
        super(supplyChallanExtendedRepository, supplyChallanMapper, supplyChallanSearchRepository);
        this.supplyChallanExtendedRepository = supplyChallanExtendedRepository;
        this.supplyChallanMapper = supplyChallanMapper;
        this.supplyChallanSearchRepository = supplyChallanSearchRepository;
    }
}
