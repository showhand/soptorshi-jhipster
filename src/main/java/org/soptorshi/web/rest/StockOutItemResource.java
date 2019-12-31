package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockOutItemQueryService;
import org.soptorshi.service.StockOutItemService;
import org.soptorshi.service.dto.StockOutItemCriteria;
import org.soptorshi.service.dto.StockOutItemDTO;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StockOutItem.
 */
@RestController
@RequestMapping("/api")
public class StockOutItemResource {

    private final Logger log = LoggerFactory.getLogger(StockOutItemResource.class);

    private static final String ENTITY_NAME = "stockOutItem";

    private final StockOutItemService stockOutItemService;

    private final StockOutItemQueryService stockOutItemQueryService;

    public StockOutItemResource(StockOutItemService stockOutItemService, StockOutItemQueryService stockOutItemQueryService) {
        this.stockOutItemService = stockOutItemService;
        this.stockOutItemQueryService = stockOutItemQueryService;
    }

    /**
     * POST  /stock-out-items : Create a new stockOutItem.
     *
     * @param stockOutItemDTO the stockOutItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockOutItemDTO, or with status 400 (Bad Request) if the stockOutItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-out-items")
    public ResponseEntity<StockOutItemDTO> createStockOutItem(@Valid @RequestBody StockOutItemDTO stockOutItemDTO) throws URISyntaxException {
        log.debug("REST request to save StockOutItem : {}", stockOutItemDTO);
        if (stockOutItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockOutItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockOutItemDTO result = stockOutItemService.save(stockOutItemDTO);
        return ResponseEntity.created(new URI("/api/stock-out-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
        if (stockOutItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockOutItemDTO result = stockOutItemService.save(stockOutItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockOutItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-out-items : get all the stockOutItems.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of stockOutItems in body
     */
    @GetMapping("/stock-out-items")
    public ResponseEntity<List<StockOutItemDTO>> getAllStockOutItems(StockOutItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockOutItems by criteria: {}", criteria);
        Page<StockOutItemDTO> page = stockOutItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-out-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /stock-out-items/count : count all the stockOutItems.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/stock-out-items/count")
    public ResponseEntity<Long> countStockOutItems(StockOutItemCriteria criteria) {
        log.debug("REST request to count StockOutItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockOutItemQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /stock-out-items/:id : get the "id" stockOutItem.
     *
     * @param id the id of the stockOutItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockOutItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-out-items/{id}")
    public ResponseEntity<StockOutItemDTO> getStockOutItem(@PathVariable Long id) {
        log.debug("REST request to get StockOutItem : {}", id);
        Optional<StockOutItemDTO> stockOutItemDTO = stockOutItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockOutItemDTO);
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
        stockOutItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/stock-out-items?query=:query : search for the stockOutItem corresponding
     * to the query.
     *
     * @param query the query of the stockOutItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/stock-out-items")
    public ResponseEntity<List<StockOutItemDTO>> searchStockOutItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StockOutItems for query {}", query);
        Page<StockOutItemDTO> page = stockOutItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/stock-out-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
