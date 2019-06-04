package org.soptorshi.web.rest;
import org.soptorshi.service.DesignationWiseAllowanceService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.DesignationWiseAllowanceDTO;
import org.soptorshi.service.dto.DesignationWiseAllowanceCriteria;
import org.soptorshi.service.DesignationWiseAllowanceQueryService;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DesignationWiseAllowance.
 */
@RestController
@RequestMapping("/api")
public class DesignationWiseAllowanceResource {

    private final Logger log = LoggerFactory.getLogger(DesignationWiseAllowanceResource.class);

    private static final String ENTITY_NAME = "designationWiseAllowance";

    private final DesignationWiseAllowanceService designationWiseAllowanceService;

    private final DesignationWiseAllowanceQueryService designationWiseAllowanceQueryService;

    public DesignationWiseAllowanceResource(DesignationWiseAllowanceService designationWiseAllowanceService, DesignationWiseAllowanceQueryService designationWiseAllowanceQueryService) {
        this.designationWiseAllowanceService = designationWiseAllowanceService;
        this.designationWiseAllowanceQueryService = designationWiseAllowanceQueryService;
    }

    /**
     * POST  /designation-wise-allowances : Create a new designationWiseAllowance.
     *
     * @param designationWiseAllowanceDTO the designationWiseAllowanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designationWiseAllowanceDTO, or with status 400 (Bad Request) if the designationWiseAllowance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designation-wise-allowances")
    public ResponseEntity<DesignationWiseAllowanceDTO> createDesignationWiseAllowance(@RequestBody DesignationWiseAllowanceDTO designationWiseAllowanceDTO) throws URISyntaxException {
        log.debug("REST request to save DesignationWiseAllowance : {}", designationWiseAllowanceDTO);
        if (designationWiseAllowanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new designationWiseAllowance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignationWiseAllowanceDTO result = designationWiseAllowanceService.save(designationWiseAllowanceDTO);
        return ResponseEntity.created(new URI("/api/designation-wise-allowances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designation-wise-allowances : Updates an existing designationWiseAllowance.
     *
     * @param designationWiseAllowanceDTO the designationWiseAllowanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designationWiseAllowanceDTO,
     * or with status 400 (Bad Request) if the designationWiseAllowanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the designationWiseAllowanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designation-wise-allowances")
    public ResponseEntity<DesignationWiseAllowanceDTO> updateDesignationWiseAllowance(@RequestBody DesignationWiseAllowanceDTO designationWiseAllowanceDTO) throws URISyntaxException {
        log.debug("REST request to update DesignationWiseAllowance : {}", designationWiseAllowanceDTO);
        if (designationWiseAllowanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DesignationWiseAllowanceDTO result = designationWiseAllowanceService.save(designationWiseAllowanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designationWiseAllowanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designation-wise-allowances : get all the designationWiseAllowances.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of designationWiseAllowances in body
     */
    @GetMapping("/designation-wise-allowances")
    public ResponseEntity<List<DesignationWiseAllowanceDTO>> getAllDesignationWiseAllowances(DesignationWiseAllowanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DesignationWiseAllowances by criteria: {}", criteria);
        Page<DesignationWiseAllowanceDTO> page = designationWiseAllowanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designation-wise-allowances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /designation-wise-allowances/count : count all the designationWiseAllowances.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/designation-wise-allowances/count")
    public ResponseEntity<Long> countDesignationWiseAllowances(DesignationWiseAllowanceCriteria criteria) {
        log.debug("REST request to count DesignationWiseAllowances by criteria: {}", criteria);
        return ResponseEntity.ok().body(designationWiseAllowanceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /designation-wise-allowances/:id : get the "id" designationWiseAllowance.
     *
     * @param id the id of the designationWiseAllowanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designationWiseAllowanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/designation-wise-allowances/{id}")
    public ResponseEntity<DesignationWiseAllowanceDTO> getDesignationWiseAllowance(@PathVariable Long id) {
        log.debug("REST request to get DesignationWiseAllowance : {}", id);
        Optional<DesignationWiseAllowanceDTO> designationWiseAllowanceDTO = designationWiseAllowanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(designationWiseAllowanceDTO);
    }

    /**
     * DELETE  /designation-wise-allowances/:id : delete the "id" designationWiseAllowance.
     *
     * @param id the id of the designationWiseAllowanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designation-wise-allowances/{id}")
    public ResponseEntity<Void> deleteDesignationWiseAllowance(@PathVariable Long id) {
        log.debug("REST request to delete DesignationWiseAllowance : {}", id);
        designationWiseAllowanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designation-wise-allowances?query=:query : search for the designationWiseAllowance corresponding
     * to the query.
     *
     * @param query the query of the designationWiseAllowance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designation-wise-allowances")
    public ResponseEntity<List<DesignationWiseAllowanceDTO>> searchDesignationWiseAllowances(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DesignationWiseAllowances for query {}", query);
        Page<DesignationWiseAllowanceDTO> page = designationWiseAllowanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designation-wise-allowances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
