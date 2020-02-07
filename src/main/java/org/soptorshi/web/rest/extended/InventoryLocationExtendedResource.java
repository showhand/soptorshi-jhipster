package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.InventoryLocationQueryService;
import org.soptorshi.service.extended.InventoryLocationExtendedService;
import org.soptorshi.web.rest.InventoryLocationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing InventoryLocation.
 */
@RestController
@RequestMapping("/api/extended")
public class InventoryLocationExtendedResource extends InventoryLocationResource {

    private final Logger log = LoggerFactory.getLogger(InventoryLocationExtendedResource.class);

    private static final String ENTITY_NAME = "inventoryLocation";

    public InventoryLocationExtendedResource(InventoryLocationExtendedService inventoryLocationExtendedService, InventoryLocationQueryService inventoryLocationQueryService) {
        super(inventoryLocationExtendedService, inventoryLocationQueryService);
    }

    /**
     * DELETE  /inventory-locations/:id : delete the "id" inventoryLocation.
     *
     * @param id the id of the inventoryLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventory-locations/{id}")
    public ResponseEntity<Void> deleteInventoryLocation(@PathVariable Long id) {
        log.debug("REST request to delete InventoryLocation : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
