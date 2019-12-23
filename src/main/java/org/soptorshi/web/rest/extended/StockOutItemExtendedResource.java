package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockOutItemQueryService;
import org.soptorshi.service.dto.StockOutItemDTO;
import org.soptorshi.service.extended.StockOutItemExtendedService;
import org.soptorshi.web.rest.StockOutItemResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing StockOutItem.
 */
@RestController
@RequestMapping("/api/extended")
public class StockOutItemExtendedResource extends StockOutItemResource {

    private final Logger log = LoggerFactory.getLogger(StockOutItemExtendedResource.class);

    private static final String ENTITY_NAME = "stockOutItem";

    public StockOutItemExtendedResource(StockOutItemExtendedService stockOutItemExtendedService, StockOutItemQueryService stockOutItemQueryService) {
        super(stockOutItemExtendedService, stockOutItemQueryService);
    }

    /**
     * PUT  /stock-out-items : Updates an existing stockOutItem.
     *
     * @param stockOutItemDTO the stockOutItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockOutItemDTO,
     * or with status 400 (Bad Request) if the stockOutItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockOutItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-out-items")
    public ResponseEntity<StockOutItemDTO> updateStockOutItem(@Valid @RequestBody StockOutItemDTO stockOutItemDTO) throws URISyntaxException {
        log.debug("REST request to update StockOutItem : {}", stockOutItemDTO);
        throw new BadRequestAlertException("Update operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * DELETE  /stock-out-items/:id : delete the "id" stockOutItem.
     *
     * @param id the id of the stockOutItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-out-items/{id}")
    public ResponseEntity<Void> deleteStockOutItem(@PathVariable Long id) {
        log.debug("REST request to delete StockOutItem : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
