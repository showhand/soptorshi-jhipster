package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.SupplySalesRepresentativeExtendedRepository;
import org.soptorshi.repository.search.SupplySalesRepresentativeSearchRepository;
import org.soptorshi.service.SupplySalesRepresentativeService;
import org.soptorshi.service.mapper.SupplySalesRepresentativeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SupplySalesRepresentative.
 */
@Service
@Transactional
public class SupplySalesRepresentativeExtendedService extends SupplySalesRepresentativeService {

    private final Logger log = LoggerFactory.getLogger(SupplySalesRepresentativeExtendedService.class);

    public SupplySalesRepresentativeExtendedService(SupplySalesRepresentativeExtendedRepository supplySalesRepresentativeExtendedRepository, SupplySalesRepresentativeMapper supplySalesRepresentativeMapper, SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository) {
        super(supplySalesRepresentativeExtendedRepository, supplySalesRepresentativeMapper, supplySalesRepresentativeSearchRepository);
    }
}
