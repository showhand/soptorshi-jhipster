package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockInItemQueryService;
import org.soptorshi.service.StockInItemService;
import org.soptorshi.service.dto.StockInItemCriteria;
import org.soptorshi.service.dto.StockInItemDTO;
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
 * REST controller for managing StockInItem.
 */
@RestController
@RequestMapping("/api")
public class StockInItemResource {

    private final Logger log = LoggerFactory.getLogger(StockInItemResource.class);

    private static final String ENTITY_NAME = "stockInItem";

    private final StockInItemService stockInItemService;

    private final StockInItemQueryService stockInItemQueryService;

    public StockInItemResource(StockInItemService stockInItemService, StockInItemQueryService stockInItemQueryService) {
        this.stockInItemService = stockInItemService;
        this.stockInItemQueryService = stockInItemQueryService;
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
        if (stockInItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockInItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockInItemDTO result = stockInItemService.save(stockInItemDTO);
        return ResponseEntity.created(new URI("/api/stock-in-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
        if (stockInItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockInItemDTO result = stockInItemService.save(stockInItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockInItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-in-items : get all the stockInItems.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of stockInItems in body
     */
    @GetMapping("/stock-in-items")
    public ResponseEntity<List<StockInItemDTO>> getAllStockInItems(StockInItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockInItems by criteria: {}", criteria);
        Page<StockInItemDTO> page = stockInItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-in-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /stock-in-items/count : count all the stockInItems.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/stock-in-items/count")
    public ResponseEntity<Long> countStockInItems(StockInItemCriteria criteria) {
        log.debug("REST request to count StockInItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockInItemQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /stock-in-items/:id : get the "id" stockInItem.
     *
     * @param id the id of the stockInItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockInItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-in-items/{id}")
    public ResponseEntity<StockInItemDTO> getStockInItem(@PathVariable Long id) {
        log.debug("REST request to get StockInItem : {}", id);
        Optional<StockInItemDTO> stockInItemDTO = stockInItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockInItemDTO);
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
        stockInItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/stock-in-items?query=:query : search for the stockInItem corresponding
     * to the query.
     *
     * @param query the query of the stockInItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/stock-in-items")
    public ResponseEntity<List<StockInItemDTO>> searchStockInItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StockInItems for query {}", query);
        Page<StockInItemDTO> page = stockInItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/stock-in-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
