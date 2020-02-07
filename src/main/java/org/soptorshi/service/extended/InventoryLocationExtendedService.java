package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.InventoryLocationRepository;
import org.soptorshi.repository.search.InventoryLocationSearchRepository;
import org.soptorshi.service.InventoryLocationService;
import org.soptorshi.service.mapper.InventoryLocationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing InventoryLocation.
 */
@Service
@Transactional
public class InventoryLocationExtendedService extends InventoryLocationService {

    private final Logger log = LoggerFactory.getLogger(InventoryLocationExtendedService.class);

    public InventoryLocationExtendedService(InventoryLocationRepository inventoryLocationRepository, InventoryLocationMapper inventoryLocationMapper, InventoryLocationSearchRepository inventoryLocationSearchRepository) {
        super(inventoryLocationRepository, inventoryLocationMapper, inventoryLocationSearchRepository);
    }
}
