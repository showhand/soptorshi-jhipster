package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockInItemQueryService;
import org.soptorshi.service.dto.StockInItemDTO;
import org.soptorshi.service.extended.StockInItemExtendedService;
import org.soptorshi.web.rest.StockInItemResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing StockInItem.
 */
@RestController
@RequestMapping("/api/extended")
public class StockInItemExtendedResource extends StockInItemResource {

    private final Logger log = LoggerFactory.getLogger(StockInItemExtendedResource.class);

    private static final String ENTITY_NAME = "stockInItem";

    public StockInItemExtendedResource(StockInItemExtendedService stockInItemExtendedService, StockInItemQueryService stockInItemQueryService) {
        super(stockInItemExtendedService, stockInItemQueryService);
    }

    /**
     * POST  /stock-in-items : Create a new stockInItem.
     *
     * @param stockInItemDTO the stockInItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockInItemDTO, or with status 400 (Bad Request) if the stockInItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-in-items")
    public ResponseEntity<StockInItemDTO> createStockInItem(@Valid @RequestBody StockInItemDTO stockInItemDTO) throws URISyntaxException {
        log.debug("REST request to save StockInItem : {}", stockInItemDTO);
        throw new BadRequestAlertException("Save operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * PUT  /stock-in-items : Updates an existing stockInItem.
     *
     * @param stockInItemDTO the stockInItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockInItemDTO,
     * or with status 400 (Bad Request) if the stockInItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockInItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-in-items")
    public ResponseEntity<StockInItemDTO> updateStockInItem(@Valid @RequestBody StockInItemDTO stockInItemDTO) throws URISyntaxException {
        log.debug("REST request to update StockInItem : {}", stockInItemDTO);
        throw new BadRequestAlertException("Update operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * DELETE  /stock-in-items/:id : delete the "id" stockInItem.
     *
     * @param id the id of the stockInItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-in-items/{id}")
    public ResponseEntity<Void> deleteStockInItem(@PathVariable Long id) {
        log.debug("REST request to delete StockInItem : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
