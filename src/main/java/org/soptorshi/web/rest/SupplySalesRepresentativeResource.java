package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplySalesRepresentativeQueryService;
import org.soptorshi.service.SupplySalesRepresentativeService;
import org.soptorshi.service.dto.SupplySalesRepresentativeCriteria;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
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
 * REST controller for managing SupplySalesRepresentative.
 */
@RestController
@RequestMapping("/api")
public class SupplySalesRepresentativeResource {

    private final Logger log = LoggerFactory.getLogger(SupplySalesRepresentativeResource.class);

    private static final String ENTITY_NAME = "supplySalesRepresentative";

    private final SupplySalesRepresentativeService supplySalesRepresentativeService;

    private final SupplySalesRepresentativeQueryService supplySalesRepresentativeQueryService;

    public SupplySalesRepresentativeResource(SupplySalesRepresentativeService supplySalesRepresentativeService, SupplySalesRepresentativeQueryService supplySalesRepresentativeQueryService) {
        this.supplySalesRepresentativeService = supplySalesRepresentativeService;
        this.supplySalesRepresentativeQueryService = supplySalesRepresentativeQueryService;
    }

    /**
     * POST  /supply-sales-representatives : Create a new supplySalesRepresentative.
     *
     * @param supplySalesRepresentativeDTO the supplySalesRepresentativeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplySalesRepresentativeDTO, or with status 400 (Bad Request) if the supplySalesRepresentative has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-sales-representatives")
    public ResponseEntity<SupplySalesRepresentativeDTO> createSupplySalesRepresentative(@Valid @RequestBody SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) throws URISyntaxException {
        log.debug("REST request to save SupplySalesRepresentative : {}", supplySalesRepresentativeDTO);
        if (supplySalesRepresentativeDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplySalesRepresentative cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplySalesRepresentativeDTO result = supplySalesRepresentativeService.save(supplySalesRepresentativeDTO);
        return ResponseEntity.created(new URI("/api/supply-sales-representatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-sales-representatives : Updates an existing supplySalesRepresentative.
     *
     * @param supplySalesRepresentativeDTO the supplySalesRepresentativeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplySalesRepresentativeDTO,
     * or with status 400 (Bad Request) if the supplySalesRepresentativeDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplySalesRepresentativeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-sales-representatives")
    public ResponseEntity<SupplySalesRepresentativeDTO> updateSupplySalesRepresentative(@Valid @RequestBody SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) throws URISyntaxException {
        log.debug("REST request to update SupplySalesRepresentative : {}", supplySalesRepresentativeDTO);
        if (supplySalesRepresentativeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplySalesRepresentativeDTO result = supplySalesRepresentativeService.save(supplySalesRepresentativeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplySalesRepresentativeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-sales-representatives : get all the supplySalesRepresentatives.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplySalesRepresentatives in body
     */
    @GetMapping("/supply-sales-representatives")
    public ResponseEntity<List<SupplySalesRepresentativeDTO>> getAllSupplySalesRepresentatives(SupplySalesRepresentativeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplySalesRepresentatives by criteria: {}", criteria);
        Page<SupplySalesRepresentativeDTO> page = supplySalesRepresentativeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-sales-representatives");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-sales-representatives/count : count all the supplySalesRepresentatives.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-sales-representatives/count")
    public ResponseEntity<Long> countSupplySalesRepresentatives(SupplySalesRepresentativeCriteria criteria) {
        log.debug("REST request to count SupplySalesRepresentatives by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplySalesRepresentativeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-sales-representatives/:id : get the "id" supplySalesRepresentative.
     *
     * @param id the id of the supplySalesRepresentativeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplySalesRepresentativeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-sales-representatives/{id}")
    public ResponseEntity<SupplySalesRepresentativeDTO> getSupplySalesRepresentative(@PathVariable Long id) {
        log.debug("REST request to get SupplySalesRepresentative : {}", id);
        Optional<SupplySalesRepresentativeDTO> supplySalesRepresentativeDTO = supplySalesRepresentativeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplySalesRepresentativeDTO);
    }

    /**
     * DELETE  /supply-sales-representatives/:id : delete the "id" supplySalesRepresentative.
     *
     * @param id the id of the supplySalesRepresentativeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-sales-representatives/{id}")
    public ResponseEntity<Void> deleteSupplySalesRepresentative(@PathVariable Long id) {
        log.debug("REST request to delete SupplySalesRepresentative : {}", id);
        supplySalesRepresentativeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-sales-representatives?query=:query : search for the supplySalesRepresentative corresponding
     * to the query.
     *
     * @param query the query of the supplySalesRepresentative search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-sales-representatives")
    public ResponseEntity<List<SupplySalesRepresentativeDTO>> searchSupplySalesRepresentatives(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplySalesRepresentatives for query {}", query);
        Page<SupplySalesRepresentativeDTO> page = supplySalesRepresentativeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-sales-representatives");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
