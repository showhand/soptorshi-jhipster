package org.soptorshi.web.rest;
import org.soptorshi.service.impl.ManufacturerServiceImpl;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ManufacturerDTO;
import org.soptorshi.service.dto.ManufacturerCriteria;
import org.soptorshi.service.ManufacturerQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/api")
public class ManufacturerResource {

    private final Logger log = LoggerFactory.getLogger(ManufacturerResource.class);

    private static final String ENTITY_NAME = "manufacturer";

    private final ManufacturerServiceImpl manufacturerServiceImpl;

    private final ManufacturerQueryService manufacturerQueryService;

    public ManufacturerResource(ManufacturerServiceImpl manufacturerServiceImpl, ManufacturerQueryService manufacturerQueryService) {
        this.manufacturerServiceImpl = manufacturerServiceImpl;
        this.manufacturerQueryService = manufacturerQueryService;
    }

    /**
     * POST  /manufacturers : Create a new manufacturer.
     *
     * @param manufacturerDTO the manufacturerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manufacturerDTO, or with status 400 (Bad Request) if the manufacturer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manufacturers")
    public ResponseEntity<ManufacturerDTO> createManufacturer(@Valid @RequestBody ManufacturerDTO manufacturerDTO) throws URISyntaxException {
        log.debug("REST request to save Manufacturer : {}", manufacturerDTO);
        if (manufacturerDTO.getId() != null) {
            throw new BadRequestAlertException("A new manufacturer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManufacturerDTO result = manufacturerServiceImpl.save(manufacturerDTO);
        return ResponseEntity.created(new URI("/api/manufacturers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /manufacturers : Updates an existing manufacturer.
     *
     * @param manufacturerDTO the manufacturerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manufacturerDTO,
     * or with status 400 (Bad Request) if the manufacturerDTO is not valid,
     * or with status 500 (Internal Server Error) if the manufacturerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/manufacturers")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@Valid @RequestBody ManufacturerDTO manufacturerDTO) throws URISyntaxException {
        log.debug("REST request to update Manufacturer : {}", manufacturerDTO);
        if (manufacturerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ManufacturerDTO result = manufacturerServiceImpl.save(manufacturerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, manufacturerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /manufacturers : get all the manufacturers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of manufacturers in body
     */
    @GetMapping("/manufacturers")
    public ResponseEntity<List<ManufacturerDTO>> getAllManufacturers(ManufacturerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Manufacturers by criteria: {}", criteria);
        Page<ManufacturerDTO> page = manufacturerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manufacturers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /manufacturers/count : count all the manufacturers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/manufacturers/count")
    public ResponseEntity<Long> countManufacturers(ManufacturerCriteria criteria) {
        log.debug("REST request to count Manufacturers by criteria: {}", criteria);
        return ResponseEntity.ok().body(manufacturerQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /manufacturers/:id : get the "id" manufacturer.
     *
     * @param id the id of the manufacturerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manufacturerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/manufacturers/{id}")
    public ResponseEntity<ManufacturerDTO> getManufacturer(@PathVariable Long id) {
        log.debug("REST request to get Manufacturer : {}", id);
        Optional<ManufacturerDTO> manufacturerDTO = manufacturerServiceImpl.findOne(id);
        return ResponseUtil.wrapOrNotFound(manufacturerDTO);
    }

    /**
     * DELETE  /manufacturers/:id : delete the "id" manufacturer.
     *
     * @param id the id of the manufacturerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/manufacturers/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        log.debug("REST request to delete Manufacturer : {}", id);
        manufacturerServiceImpl.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/manufacturers?query=:query : search for the manufacturer corresponding
     * to the query.
     *
     * @param query the query of the manufacturer search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/manufacturers")
    public ResponseEntity<List<ManufacturerDTO>> searchManufacturers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Manufacturers for query {}", query);
        Page<ManufacturerDTO> page = manufacturerServiceImpl.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/manufacturers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
