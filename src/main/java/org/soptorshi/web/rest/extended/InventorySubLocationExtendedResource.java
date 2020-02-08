package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.InventorySubLocationQueryService;
import org.soptorshi.service.extended.InventorySubLocationExtendedService;
import org.soptorshi.web.rest.InventorySubLocationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing InventorySubLocation.
 */
@RestController
@RequestMapping("/api/extended")
public class InventorySubLocationExtendedResource extends InventorySubLocationResource {

    private final Logger log = LoggerFactory.getLogger(InventorySubLocationExtendedResource.class);

    private static final String ENTITY_NAME = "inventorySubLocation";

    public InventorySubLocationExtendedResource(InventorySubLocationExtendedService inventorySubLocationExtendedService
        , InventorySubLocationQueryService inventorySubLocationQueryService) {
        super(inventorySubLocationExtendedService, inventorySubLocationQueryService);
    }

    /**
     * DELETE  /inventory-sub-locations/:id : delete the "id" inventorySubLocation.
     *
     * @param id the id of the inventorySubLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventory-sub-locations/{id}")
    public ResponseEntity<Void> deleteInventorySubLocation(@PathVariable Long id) {
        log.debug("REST request to delete InventorySubLocation : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
