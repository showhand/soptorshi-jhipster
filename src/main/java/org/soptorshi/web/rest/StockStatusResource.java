package org.soptorshi.web.rest;
import org.soptorshi.service.StockStatusService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.dto.StockStatusCriteria;
import org.soptorshi.service.StockStatusQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing StockStatus.
 */
@RestController
@RequestMapping("/api")
public class StockStatusResource {

    private final Logger log = LoggerFactory.getLogger(StockStatusResource.class);

    private static final String ENTITY_NAME = "stockStatus";

    private final StockStatusService stockStatusService;

    private final StockStatusQueryService stockStatusQueryService;

    public StockStatusResource(StockStatusService stockStatusService, StockStatusQueryService stockStatusQueryService) {
        this.stockStatusService = stockStatusService;
        this.stockStatusQueryService = stockStatusQueryService;
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
        if (stockStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockStatusDTO result = stockStatusService.save(stockStatusDTO);
        return ResponseEntity.created(new URI("/api/stock-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
        if (stockStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockStatusDTO result = stockStatusService.save(stockStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-statuses : get all the stockStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of stockStatuses in body
     */
    @GetMapping("/stock-statuses")
    public ResponseEntity<List<StockStatusDTO>> getAllStockStatuses(StockStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockStatuses by criteria: {}", criteria);
        Page<StockStatusDTO> page = stockStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /stock-statuses/count : count all the stockStatuses.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/stock-statuses/count")
    public ResponseEntity<Long> countStockStatuses(StockStatusCriteria criteria) {
        log.debug("REST request to count StockStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockStatusQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /stock-statuses/:id : get the "id" stockStatus.
     *
     * @param id the id of the stockStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-statuses/{id}")
    public ResponseEntity<StockStatusDTO> getStockStatus(@PathVariable Long id) {
        log.debug("REST request to get StockStatus : {}", id);
        Optional<StockStatusDTO> stockStatusDTO = stockStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockStatusDTO);
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
        stockStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/stock-statuses?query=:query : search for the stockStatus corresponding
     * to the query.
     *
     * @param query the query of the stockStatus search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/stock-statuses")
    public ResponseEntity<List<StockStatusDTO>> searchStockStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StockStatuses for query {}", query);
        Page<StockStatusDTO> page = stockStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/stock-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
