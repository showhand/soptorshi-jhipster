package org.soptorshi.web.rest;
import org.soptorshi.service.VendorContactPersonService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.VendorContactPersonDTO;
import org.soptorshi.service.dto.VendorContactPersonCriteria;
import org.soptorshi.service.VendorContactPersonQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing VendorContactPerson.
 */
@RestController
@RequestMapping("/api")
public class VendorContactPersonResource {

    private final Logger log = LoggerFactory.getLogger(VendorContactPersonResource.class);

    private static final String ENTITY_NAME = "vendorContactPerson";

    private final VendorContactPersonService vendorContactPersonService;

    private final VendorContactPersonQueryService vendorContactPersonQueryService;

    public VendorContactPersonResource(VendorContactPersonService vendorContactPersonService, VendorContactPersonQueryService vendorContactPersonQueryService) {
        this.vendorContactPersonService = vendorContactPersonService;
        this.vendorContactPersonQueryService = vendorContactPersonQueryService;
    }

    /**
     * POST  /vendor-contact-people : Create a new vendorContactPerson.
     *
     * @param vendorContactPersonDTO the vendorContactPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendorContactPersonDTO, or with status 400 (Bad Request) if the vendorContactPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendor-contact-people")
    public ResponseEntity<VendorContactPersonDTO> createVendorContactPerson(@RequestBody VendorContactPersonDTO vendorContactPersonDTO) throws URISyntaxException {
        log.debug("REST request to save VendorContactPerson : {}", vendorContactPersonDTO);
        if (vendorContactPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new vendorContactPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendorContactPersonDTO result = vendorContactPersonService.save(vendorContactPersonDTO);
        return ResponseEntity.created(new URI("/api/vendor-contact-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendor-contact-people : Updates an existing vendorContactPerson.
     *
     * @param vendorContactPersonDTO the vendorContactPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendorContactPersonDTO,
     * or with status 400 (Bad Request) if the vendorContactPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the vendorContactPersonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendor-contact-people")
    public ResponseEntity<VendorContactPersonDTO> updateVendorContactPerson(@RequestBody VendorContactPersonDTO vendorContactPersonDTO) throws URISyntaxException {
        log.debug("REST request to update VendorContactPerson : {}", vendorContactPersonDTO);
        if (vendorContactPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VendorContactPersonDTO result = vendorContactPersonService.save(vendorContactPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vendorContactPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vendor-contact-people : get all the vendorContactPeople.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vendorContactPeople in body
     */
    @GetMapping("/vendor-contact-people")
    public ResponseEntity<List<VendorContactPersonDTO>> getAllVendorContactPeople(VendorContactPersonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VendorContactPeople by criteria: {}", criteria);
        Page<VendorContactPersonDTO> page = vendorContactPersonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vendor-contact-people");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /vendor-contact-people/count : count all the vendorContactPeople.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/vendor-contact-people/count")
    public ResponseEntity<Long> countVendorContactPeople(VendorContactPersonCriteria criteria) {
        log.debug("REST request to count VendorContactPeople by criteria: {}", criteria);
        return ResponseEntity.ok().body(vendorContactPersonQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /vendor-contact-people/:id : get the "id" vendorContactPerson.
     *
     * @param id the id of the vendorContactPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendorContactPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vendor-contact-people/{id}")
    public ResponseEntity<VendorContactPersonDTO> getVendorContactPerson(@PathVariable Long id) {
        log.debug("REST request to get VendorContactPerson : {}", id);
        Optional<VendorContactPersonDTO> vendorContactPersonDTO = vendorContactPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendorContactPersonDTO);
    }

    /**
     * DELETE  /vendor-contact-people/:id : delete the "id" vendorContactPerson.
     *
     * @param id the id of the vendorContactPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendor-contact-people/{id}")
    public ResponseEntity<Void> deleteVendorContactPerson(@PathVariable Long id) {
        log.debug("REST request to delete VendorContactPerson : {}", id);
        vendorContactPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vendor-contact-people?query=:query : search for the vendorContactPerson corresponding
     * to the query.
     *
     * @param query the query of the vendorContactPerson search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/vendor-contact-people")
    public ResponseEntity<List<VendorContactPersonDTO>> searchVendorContactPeople(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VendorContactPeople for query {}", query);
        Page<VendorContactPersonDTO> page = vendorContactPersonService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/vendor-contact-people");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
