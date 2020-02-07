package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.InventorySubLocationRepository;
import org.soptorshi.repository.search.InventorySubLocationSearchRepository;
import org.soptorshi.service.InventorySubLocationService;
import org.soptorshi.service.mapper.InventorySubLocationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing InventorySubLocation.
 */
@Service
@Transactional
public class InventorySubLocationExtendedService extends InventorySubLocationService {

    private final Logger log = LoggerFactory.getLogger(InventorySubLocationExtendedService.class);

    public InventorySubLocationExtendedService(InventorySubLocationRepository inventorySubLocationRepository, InventorySubLocationMapper inventorySubLocationMapper, InventorySubLocationSearchRepository inventorySubLocationSearchRepository) {
        super(inventorySubLocationRepository, inventorySubLocationMapper, inventorySubLocationSearchRepository);
    }
}
