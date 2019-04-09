package org.soptorshi.web.rest;
import org.soptorshi.service.DesignationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.DesignationDTO;
import org.soptorshi.service.dto.DesignationCriteria;
import org.soptorshi.service.DesignationQueryService;
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
 * REST controller for managing Designation.
 */
@RestController
@RequestMapping("/api")
public class DesignationResource {

    private final Logger log = LoggerFactory.getLogger(DesignationResource.class);

    private static final String ENTITY_NAME = "designation";

    private final DesignationService designationService;

    private final DesignationQueryService designationQueryService;

    public DesignationResource(DesignationService designationService, DesignationQueryService designationQueryService) {
        this.designationService = designationService;
        this.designationQueryService = designationQueryService;
    }

    /**
     * POST  /designations : Create a new designation.
     *
     * @param designationDTO the designationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designationDTO, or with status 400 (Bad Request) if the designation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designations")
    public ResponseEntity<DesignationDTO> createDesignation(@RequestBody DesignationDTO designationDTO) throws URISyntaxException {
        log.debug("REST request to save Designation : {}", designationDTO);
        if (designationDTO.getId() != null) {
            throw new BadRequestAlertException("A new designation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignationDTO result = designationService.save(designationDTO);
        return ResponseEntity.created(new URI("/api/designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designations : Updates an existing designation.
     *
     * @param designationDTO the designationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designationDTO,
     * or with status 400 (Bad Request) if the designationDTO is not valid,
     * or with status 500 (Internal Server Error) if the designationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designations")
    public ResponseEntity<DesignationDTO> updateDesignation(@RequestBody DesignationDTO designationDTO) throws URISyntaxException {
        log.debug("REST request to update Designation : {}", designationDTO);
        if (designationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DesignationDTO result = designationService.save(designationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designations : get all the designations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of designations in body
     */
    @GetMapping("/designations")
    public ResponseEntity<List<DesignationDTO>> getAllDesignations(DesignationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Designations by criteria: {}", criteria);
        Page<DesignationDTO> page = designationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /designations/count : count all the designations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/designations/count")
    public ResponseEntity<Long> countDesignations(DesignationCriteria criteria) {
        log.debug("REST request to count Designations by criteria: {}", criteria);
        return ResponseEntity.ok().body(designationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /designations/:id : get the "id" designation.
     *
     * @param id the id of the designationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/designations/{id}")
    public ResponseEntity<DesignationDTO> getDesignation(@PathVariable Long id) {
        log.debug("REST request to get Designation : {}", id);
        Optional<DesignationDTO> designationDTO = designationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(designationDTO);
    }

    /**
     * DELETE  /designations/:id : delete the "id" designation.
     *
     * @param id the id of the designationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designations/{id}")
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long id) {
        log.debug("REST request to delete Designation : {}", id);
        designationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designations?query=:query : search for the designation corresponding
     * to the query.
     *
     * @param query the query of the designation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designations")
    public ResponseEntity<List<DesignationDTO>> searchDesignations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Designations for query {}", query);
        Page<DesignationDTO> page = designationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
