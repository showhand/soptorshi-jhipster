package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockInProcessQueryService;
import org.soptorshi.service.StockInProcessService;
import org.soptorshi.service.dto.StockInProcessCriteria;
import org.soptorshi.service.dto.StockInProcessDTO;
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
 * REST controller for managing StockInProcess.
 */
@RestController
@RequestMapping("/api")
public class StockInProcessResource {

    private final Logger log = LoggerFactory.getLogger(StockInProcessResource.class);

    private static final String ENTITY_NAME = "stockInProcess";

    private final StockInProcessService stockInProcessService;

    private final StockInProcessQueryService stockInProcessQueryService;

    public StockInProcessResource(StockInProcessService stockInProcessService, StockInProcessQueryService stockInProcessQueryService) {
        this.stockInProcessService = stockInProcessService;
        this.stockInProcessQueryService = stockInProcessQueryService;
    }

    /**
     * POST  /stock-in-processes : Create a new stockInProcess.
     *
     * @param stockInProcessDTO the stockInProcessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockInProcessDTO, or with status 400 (Bad Request) if the stockInProcess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-in-processes")
    public ResponseEntity<StockInProcessDTO> createStockInProcess(@Valid @RequestBody StockInProcessDTO stockInProcessDTO) throws URISyntaxException {
        log.debug("REST request to save StockInProcess : {}", stockInProcessDTO);
        if (stockInProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockInProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockInProcessDTO result = stockInProcessService.save(stockInProcessDTO);
        return ResponseEntity.created(new URI("/api/stock-in-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-in-processes : Updates an existing stockInProcess.
     *
     * @param stockInProcessDTO the stockInProcessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockInProcessDTO,
     * or with status 400 (Bad Request) if the stockInProcessDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockInProcessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-in-processes")
    public ResponseEntity<StockInProcessDTO> updateStockInProcess(@Valid @RequestBody StockInProcessDTO stockInProcessDTO) throws URISyntaxException {
        log.debug("REST request to update StockInProcess : {}", stockInProcessDTO);
        if (stockInProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockInProcessDTO result = stockInProcessService.save(stockInProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockInProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-in-processes : get all the stockInProcesses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of stockInProcesses in body
     */
    @GetMapping("/stock-in-processes")
    public ResponseEntity<List<StockInProcessDTO>> getAllStockInProcesses(StockInProcessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockInProcesses by criteria: {}", criteria);
        Page<StockInProcessDTO> page = stockInProcessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-in-processes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /stock-in-processes/count : count all the stockInProcesses.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/stock-in-processes/count")
    public ResponseEntity<Long> countStockInProcesses(StockInProcessCriteria criteria) {
        log.debug("REST request to count StockInProcesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockInProcessQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /stock-in-processes/:id : get the "id" stockInProcess.
     *
     * @param id the id of the stockInProcessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockInProcessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-in-processes/{id}")
    public ResponseEntity<StockInProcessDTO> getStockInProcess(@PathVariable Long id) {
        log.debug("REST request to get StockInProcess : {}", id);
        Optional<StockInProcessDTO> stockInProcessDTO = stockInProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockInProcessDTO);
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
        stockInProcessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/stock-in-processes?query=:query : search for the stockInProcess corresponding
     * to the query.
     *
     * @param query the query of the stockInProcess search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/stock-in-processes")
    public ResponseEntity<List<StockInProcessDTO>> searchStockInProcesses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StockInProcesses for query {}", query);
        Page<StockInProcessDTO> page = stockInProcessService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/stock-in-processes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
