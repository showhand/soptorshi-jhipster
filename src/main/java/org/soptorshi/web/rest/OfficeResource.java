package org.soptorshi.web.rest;
import org.soptorshi.service.OfficeService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.OfficeDTO;
import org.soptorshi.service.dto.OfficeCriteria;
import org.soptorshi.service.OfficeQueryService;
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
 * REST controller for managing Office.
 */
@RestController
@RequestMapping("/api")
public class OfficeResource {

    private final Logger log = LoggerFactory.getLogger(OfficeResource.class);

    private static final String ENTITY_NAME = "office";

    private final OfficeService officeService;

    private final OfficeQueryService officeQueryService;

    public OfficeResource(OfficeService officeService, OfficeQueryService officeQueryService) {
        this.officeService = officeService;
        this.officeQueryService = officeQueryService;
    }

    /**
     * POST  /offices : Create a new office.
     *
     * @param officeDTO the officeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new officeDTO, or with status 400 (Bad Request) if the office has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offices")
    public ResponseEntity<OfficeDTO> createOffice(@RequestBody OfficeDTO officeDTO) throws URISyntaxException {
        log.debug("REST request to save Office : {}", officeDTO);
        if (officeDTO.getId() != null) {
            throw new BadRequestAlertException("A new office cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficeDTO result = officeService.save(officeDTO);
        return ResponseEntity.created(new URI("/api/offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offices : Updates an existing office.
     *
     * @param officeDTO the officeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated officeDTO,
     * or with status 400 (Bad Request) if the officeDTO is not valid,
     * or with status 500 (Internal Server Error) if the officeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offices")
    public ResponseEntity<OfficeDTO> updateOffice(@RequestBody OfficeDTO officeDTO) throws URISyntaxException {
        log.debug("REST request to update Office : {}", officeDTO);
        if (officeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfficeDTO result = officeService.save(officeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, officeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offices : get all the offices.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of offices in body
     */
    @GetMapping("/offices")
    public ResponseEntity<List<OfficeDTO>> getAllOffices(OfficeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Offices by criteria: {}", criteria);
        Page<OfficeDTO> page = officeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /offices/count : count all the offices.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/offices/count")
    public ResponseEntity<Long> countOffices(OfficeCriteria criteria) {
        log.debug("REST request to count Offices by criteria: {}", criteria);
        return ResponseEntity.ok().body(officeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /offices/:id : get the "id" office.
     *
     * @param id the id of the officeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the officeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/offices/{id}")
    public ResponseEntity<OfficeDTO> getOffice(@PathVariable Long id) {
        log.debug("REST request to get Office : {}", id);
        Optional<OfficeDTO> officeDTO = officeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officeDTO);
    }

    /**
     * DELETE  /offices/:id : delete the "id" office.
     *
     * @param id the id of the officeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offices/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        log.debug("REST request to delete Office : {}", id);
        officeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/offices?query=:query : search for the office corresponding
     * to the query.
     *
     * @param query the query of the office search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/offices")
    public ResponseEntity<List<OfficeDTO>> searchOffices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Offices for query {}", query);
        Page<OfficeDTO> page = officeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/offices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
