package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyMoneyCollectionQueryService;
import org.soptorshi.service.SupplyMoneyCollectionService;
import org.soptorshi.service.dto.SupplyMoneyCollectionCriteria;
import org.soptorshi.service.dto.SupplyMoneyCollectionDTO;
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
 * REST controller for managing SupplyMoneyCollection.
 */
@RestController
@RequestMapping("/api")
public class SupplyMoneyCollectionResource {

    private final Logger log = LoggerFactory.getLogger(SupplyMoneyCollectionResource.class);

    private static final String ENTITY_NAME = "supplyMoneyCollection";

    private final SupplyMoneyCollectionService supplyMoneyCollectionService;

    private final SupplyMoneyCollectionQueryService supplyMoneyCollectionQueryService;

    public SupplyMoneyCollectionResource(SupplyMoneyCollectionService supplyMoneyCollectionService, SupplyMoneyCollectionQueryService supplyMoneyCollectionQueryService) {
        this.supplyMoneyCollectionService = supplyMoneyCollectionService;
        this.supplyMoneyCollectionQueryService = supplyMoneyCollectionQueryService;
    }

    /**
     * POST  /supply-money-collections : Create a new supplyMoneyCollection.
     *
     * @param supplyMoneyCollectionDTO the supplyMoneyCollectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyMoneyCollectionDTO, or with status 400 (Bad Request) if the supplyMoneyCollection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-money-collections")
    public ResponseEntity<SupplyMoneyCollectionDTO> createSupplyMoneyCollection(@Valid @RequestBody SupplyMoneyCollectionDTO supplyMoneyCollectionDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyMoneyCollection : {}", supplyMoneyCollectionDTO);
        if (supplyMoneyCollectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyMoneyCollection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyMoneyCollectionDTO result = supplyMoneyCollectionService.save(supplyMoneyCollectionDTO);
        return ResponseEntity.created(new URI("/api/supply-money-collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-money-collections : Updates an existing supplyMoneyCollection.
     *
     * @param supplyMoneyCollectionDTO the supplyMoneyCollectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyMoneyCollectionDTO,
     * or with status 400 (Bad Request) if the supplyMoneyCollectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyMoneyCollectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-money-collections")
    public ResponseEntity<SupplyMoneyCollectionDTO> updateSupplyMoneyCollection(@Valid @RequestBody SupplyMoneyCollectionDTO supplyMoneyCollectionDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyMoneyCollection : {}", supplyMoneyCollectionDTO);
        if (supplyMoneyCollectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyMoneyCollectionDTO result = supplyMoneyCollectionService.save(supplyMoneyCollectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyMoneyCollectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-money-collections : get all the supplyMoneyCollections.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyMoneyCollections in body
     */
    @GetMapping("/supply-money-collections")
    public ResponseEntity<List<SupplyMoneyCollectionDTO>> getAllSupplyMoneyCollections(SupplyMoneyCollectionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyMoneyCollections by criteria: {}", criteria);
        Page<SupplyMoneyCollectionDTO> page = supplyMoneyCollectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-money-collections");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-money-collections/count : count all the supplyMoneyCollections.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-money-collections/count")
    public ResponseEntity<Long> countSupplyMoneyCollections(SupplyMoneyCollectionCriteria criteria) {
        log.debug("REST request to count SupplyMoneyCollections by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyMoneyCollectionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-money-collections/:id : get the "id" supplyMoneyCollection.
     *
     * @param id the id of the supplyMoneyCollectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyMoneyCollectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-money-collections/{id}")
    public ResponseEntity<SupplyMoneyCollectionDTO> getSupplyMoneyCollection(@PathVariable Long id) {
        log.debug("REST request to get SupplyMoneyCollection : {}", id);
        Optional<SupplyMoneyCollectionDTO> supplyMoneyCollectionDTO = supplyMoneyCollectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyMoneyCollectionDTO);
    }

    /**
     * DELETE  /supply-money-collections/:id : delete the "id" supplyMoneyCollection.
     *
     * @param id the id of the supplyMoneyCollectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-money-collections/{id}")
    public ResponseEntity<Void> deleteSupplyMoneyCollection(@PathVariable Long id) {
        log.debug("REST request to delete SupplyMoneyCollection : {}", id);
        supplyMoneyCollectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-money-collections?query=:query : search for the supplyMoneyCollection corresponding
     * to the query.
     *
     * @param query the query of the supplyMoneyCollection search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-money-collections")
    public ResponseEntity<List<SupplyMoneyCollectionDTO>> searchSupplyMoneyCollections(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyMoneyCollections for query {}", query);
        Page<SupplyMoneyCollectionDTO> page = supplyMoneyCollectionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-money-collections");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
