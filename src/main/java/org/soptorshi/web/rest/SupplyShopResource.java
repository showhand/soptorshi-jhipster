package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyShopQueryService;
import org.soptorshi.service.SupplyShopService;
import org.soptorshi.service.dto.SupplyShopCriteria;
import org.soptorshi.service.dto.SupplyShopDTO;
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
 * REST controller for managing SupplyShop.
 */
@RestController
@RequestMapping("/api")
public class SupplyShopResource {

    private final Logger log = LoggerFactory.getLogger(SupplyShopResource.class);

    private static final String ENTITY_NAME = "supplyShop";

    private final SupplyShopService supplyShopService;

    private final SupplyShopQueryService supplyShopQueryService;

    public SupplyShopResource(SupplyShopService supplyShopService, SupplyShopQueryService supplyShopQueryService) {
        this.supplyShopService = supplyShopService;
        this.supplyShopQueryService = supplyShopQueryService;
    }

    /**
     * POST  /supply-shops : Create a new supplyShop.
     *
     * @param supplyShopDTO the supplyShopDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyShopDTO, or with status 400 (Bad Request) if the supplyShop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-shops")
    public ResponseEntity<SupplyShopDTO> createSupplyShop(@Valid @RequestBody SupplyShopDTO supplyShopDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyShop : {}", supplyShopDTO);
        if (supplyShopDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyShop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyShopDTO result = supplyShopService.save(supplyShopDTO);
        return ResponseEntity.created(new URI("/api/supply-shops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-shops : Updates an existing supplyShop.
     *
     * @param supplyShopDTO the supplyShopDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyShopDTO,
     * or with status 400 (Bad Request) if the supplyShopDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyShopDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-shops")
    public ResponseEntity<SupplyShopDTO> updateSupplyShop(@Valid @RequestBody SupplyShopDTO supplyShopDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyShop : {}", supplyShopDTO);
        if (supplyShopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyShopDTO result = supplyShopService.save(supplyShopDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyShopDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-shops : get all the supplyShops.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyShops in body
     */
    @GetMapping("/supply-shops")
    public ResponseEntity<List<SupplyShopDTO>> getAllSupplyShops(SupplyShopCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyShops by criteria: {}", criteria);
        Page<SupplyShopDTO> page = supplyShopQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-shops");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-shops/count : count all the supplyShops.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-shops/count")
    public ResponseEntity<Long> countSupplyShops(SupplyShopCriteria criteria) {
        log.debug("REST request to count SupplyShops by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyShopQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-shops/:id : get the "id" supplyShop.
     *
     * @param id the id of the supplyShopDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyShopDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-shops/{id}")
    public ResponseEntity<SupplyShopDTO> getSupplyShop(@PathVariable Long id) {
        log.debug("REST request to get SupplyShop : {}", id);
        Optional<SupplyShopDTO> supplyShopDTO = supplyShopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyShopDTO);
    }

    /**
     * DELETE  /supply-shops/:id : delete the "id" supplyShop.
     *
     * @param id the id of the supplyShopDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-shops/{id}")
    public ResponseEntity<Void> deleteSupplyShop(@PathVariable Long id) {
        log.debug("REST request to delete SupplyShop : {}", id);
        supplyShopService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-shops?query=:query : search for the supplyShop corresponding
     * to the query.
     *
     * @param query the query of the supplyShop search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-shops")
    public ResponseEntity<List<SupplyShopDTO>> searchSupplyShops(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyShops for query {}", query);
        Page<SupplyShopDTO> page = supplyShopService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-shops");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
