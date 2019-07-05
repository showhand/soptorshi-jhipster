package org.soptorshi.web.rest;
import org.soptorshi.service.RequisitionDetailsService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.RequisitionDetailsDTO;
import org.soptorshi.service.dto.RequisitionDetailsCriteria;
import org.soptorshi.service.RequisitionDetailsQueryService;
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
 * REST controller for managing RequisitionDetails.
 */
@RestController
@RequestMapping("/api")
public class RequisitionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RequisitionDetailsResource.class);

    private static final String ENTITY_NAME = "requisitionDetails";

    private final RequisitionDetailsService requisitionDetailsService;

    private final RequisitionDetailsQueryService requisitionDetailsQueryService;

    public RequisitionDetailsResource(RequisitionDetailsService requisitionDetailsService, RequisitionDetailsQueryService requisitionDetailsQueryService) {
        this.requisitionDetailsService = requisitionDetailsService;
        this.requisitionDetailsQueryService = requisitionDetailsQueryService;
    }

    /**
     * POST  /requisition-details : Create a new requisitionDetails.
     *
     * @param requisitionDetailsDTO the requisitionDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requisitionDetailsDTO, or with status 400 (Bad Request) if the requisitionDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requisition-details")
    public ResponseEntity<RequisitionDetailsDTO> createRequisitionDetails(@RequestBody RequisitionDetailsDTO requisitionDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save RequisitionDetails : {}", requisitionDetailsDTO);
        if (requisitionDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisitionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitionDetailsDTO result = requisitionDetailsService.save(requisitionDetailsDTO);
        return ResponseEntity.created(new URI("/api/requisition-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requisition-details : Updates an existing requisitionDetails.
     *
     * @param requisitionDetailsDTO the requisitionDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requisitionDetailsDTO,
     * or with status 400 (Bad Request) if the requisitionDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the requisitionDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requisition-details")
    public ResponseEntity<RequisitionDetailsDTO> updateRequisitionDetails(@RequestBody RequisitionDetailsDTO requisitionDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update RequisitionDetails : {}", requisitionDetailsDTO);
        if (requisitionDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitionDetailsDTO result = requisitionDetailsService.save(requisitionDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requisitionDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requisition-details : get all the requisitionDetails.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of requisitionDetails in body
     */
    @GetMapping("/requisition-details")
    public ResponseEntity<List<RequisitionDetailsDTO>> getAllRequisitionDetails(RequisitionDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RequisitionDetails by criteria: {}", criteria);
        Page<RequisitionDetailsDTO> page = requisitionDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requisition-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /requisition-details/count : count all the requisitionDetails.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/requisition-details/count")
    public ResponseEntity<Long> countRequisitionDetails(RequisitionDetailsCriteria criteria) {
        log.debug("REST request to count RequisitionDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(requisitionDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /requisition-details/:id : get the "id" requisitionDetails.
     *
     * @param id the id of the requisitionDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requisitionDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/requisition-details/{id}")
    public ResponseEntity<RequisitionDetailsDTO> getRequisitionDetails(@PathVariable Long id) {
        log.debug("REST request to get RequisitionDetails : {}", id);
        Optional<RequisitionDetailsDTO> requisitionDetailsDTO = requisitionDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisitionDetailsDTO);
    }

    /**
     * DELETE  /requisition-details/:id : delete the "id" requisitionDetails.
     *
     * @param id the id of the requisitionDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requisition-details/{id}")
    public ResponseEntity<Void> deleteRequisitionDetails(@PathVariable Long id) {
        log.debug("REST request to delete RequisitionDetails : {}", id);
        requisitionDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/requisition-details?query=:query : search for the requisitionDetails corresponding
     * to the query.
     *
     * @param query the query of the requisitionDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/requisition-details")
    public ResponseEntity<List<RequisitionDetailsDTO>> searchRequisitionDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequisitionDetails for query {}", query);
        Page<RequisitionDetailsDTO> page = requisitionDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/requisition-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
