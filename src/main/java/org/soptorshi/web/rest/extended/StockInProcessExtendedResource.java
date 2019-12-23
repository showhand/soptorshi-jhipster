package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockInProcessQueryService;
import org.soptorshi.service.extended.StockInProcessExtendedService;
import org.soptorshi.web.rest.StockInProcessResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing StockInProcess.
 */
@RestController
@RequestMapping("/api/extended")
public class StockInProcessExtendedResource extends StockInProcessResource {

    private final Logger log = LoggerFactory.getLogger(StockInProcessExtendedResource.class);

    private static final String ENTITY_NAME = "stockInProcess";

    public StockInProcessExtendedResource(StockInProcessExtendedService stockInProcessExtendedService, StockInProcessQueryService stockInProcessQueryService) {
        super(stockInProcessExtendedService, stockInProcessQueryService);
    }

    /**
     * DELETE  /stock-in-processes/:id : delete the "id" stockInProcess.
     *
     * @param id the id of the stockInProcessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-in-processes/{id}")
    public ResponseEntity<Void> deleteStockInProcess(@PathVariable Long id) {
        log.debug("REST request to delete StockInProcess : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
