package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockStatusQueryService;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.extended.StockStatusExtendedService;
import org.soptorshi.web.rest.StockStatusResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing StockStatus.
 */
@RestController
@RequestMapping("/api/extended")
public class StockStatusExtendedResource extends StockStatusResource {

    private final Logger log = LoggerFactory.getLogger(StockStatusExtendedResource.class);

    private static final String ENTITY_NAME = "stockStatus";

    public StockStatusExtendedResource(StockStatusExtendedService stockStatusExtendedService, StockStatusQueryService stockStatusQueryService) {
       super(stockStatusExtendedService, stockStatusQueryService);
    }

    /**
     * POST  /stock-statuses : Create a new stockStatus.
     *
     * @param stockStatusDTO the stockStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockStatusDTO, or with status 400 (Bad Request) if the stockStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-statuses")
    public ResponseEntity<StockStatusDTO> createStockStatus(@Valid @RequestBody StockStatusDTO stockStatusDTO) throws URISyntaxException {
        log.debug("REST request to save StockStatus : {}", stockStatusDTO);
        throw new BadRequestAlertException("Save operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * PUT  /stock-statuses : Updates an existing stockStatus.
     *
     * @param stockStatusDTO the stockStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockStatusDTO,
     * or with status 400 (Bad Request) if the stockStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-statuses")
    public ResponseEntity<StockStatusDTO> updateStockStatus(@Valid @RequestBody StockStatusDTO stockStatusDTO) throws URISyntaxException {
        log.debug("REST request to update StockStatus : {}", stockStatusDTO);
        throw new BadRequestAlertException("Update operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * DELETE  /stock-statuses/:id : delete the "id" stockStatus.
     *
     * @param id the id of the stockStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-statuses/{id}")
    public ResponseEntity<Void> deleteStockStatus(@PathVariable Long id) {
        log.debug("REST request to delete StockStatus : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
