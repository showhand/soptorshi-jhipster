package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ProductionQueryService;
import org.soptorshi.service.ProductionService;
import org.soptorshi.service.dto.ProductionCriteria;
import org.soptorshi.service.dto.ProductionDTO;
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
 * REST controller for managing Production.
 */
@RestController
@RequestMapping("/api")
public class ProductionResource {

    private final Logger log = LoggerFactory.getLogger(ProductionResource.class);

    private static final String ENTITY_NAME = "production";

    private final ProductionService productionService;

    private final ProductionQueryService productionQueryService;

    public ProductionResource(ProductionService productionService, ProductionQueryService productionQueryService) {
        this.productionService = productionService;
        this.productionQueryService = productionQueryService;
    }

    /**
     * POST  /productions : Create a new production.
     *
     * @param productionDTO the productionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productionDTO, or with status 400 (Bad Request) if the production has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productions")
    public ResponseEntity<ProductionDTO> createProduction(@Valid @RequestBody ProductionDTO productionDTO) throws URISyntaxException {
        log.debug("REST request to save Production : {}", productionDTO);
        if (productionDTO.getId() != null) {
            throw new BadRequestAlertException("A new production cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionDTO result = productionService.save(productionDTO);
        return ResponseEntity.created(new URI("/api/productions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productions : Updates an existing production.
     *
     * @param productionDTO the productionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productionDTO,
     * or with status 400 (Bad Request) if the productionDTO is not valid,
     * or with status 500 (Internal Server Error) if the productionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productions")
    public ResponseEntity<ProductionDTO> updateProduction(@Valid @RequestBody ProductionDTO productionDTO) throws URISyntaxException {
        log.debug("REST request to update Production : {}", productionDTO);
        if (productionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionDTO result = productionService.save(productionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productions : get all the productions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of productions in body
     */
    @GetMapping("/productions")
    public ResponseEntity<List<ProductionDTO>> getAllProductions(ProductionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Productions by criteria: {}", criteria);
        Page<ProductionDTO> page = productionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /productions/count : count all the productions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/productions/count")
    public ResponseEntity<Long> countProductions(ProductionCriteria criteria) {
        log.debug("REST request to count Productions by criteria: {}", criteria);
        return ResponseEntity.ok().body(productionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /productions/:id : get the "id" production.
     *
     * @param id the id of the productionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/productions/{id}")
    public ResponseEntity<ProductionDTO> getProduction(@PathVariable Long id) {
        log.debug("REST request to get Production : {}", id);
        Optional<ProductionDTO> productionDTO = productionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionDTO);
    }

    /**
     * DELETE  /productions/:id : delete the "id" production.
     *
     * @param id the id of the productionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productions/{id}")
    public ResponseEntity<Void> deleteProduction(@PathVariable Long id) {
        log.debug("REST request to delete Production : {}", id);
        productionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/productions?query=:query : search for the production corresponding
     * to the query.
     *
     * @param query the query of the production search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/productions")
    public ResponseEntity<List<ProductionDTO>> searchProductions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Productions for query {}", query);
        Page<ProductionDTO> page = productionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/productions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
