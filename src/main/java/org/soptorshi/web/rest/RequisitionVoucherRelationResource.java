package org.soptorshi.web.rest;
import org.soptorshi.service.RequisitionVoucherRelationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;
import org.soptorshi.service.dto.RequisitionVoucherRelationCriteria;
import org.soptorshi.service.RequisitionVoucherRelationQueryService;
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
 * REST controller for managing RequisitionVoucherRelation.
 */
@RestController
@RequestMapping("/api")
public class RequisitionVoucherRelationResource {

    private final Logger log = LoggerFactory.getLogger(RequisitionVoucherRelationResource.class);

    private static final String ENTITY_NAME = "requisitionVoucherRelation";

    private final RequisitionVoucherRelationService requisitionVoucherRelationService;

    private final RequisitionVoucherRelationQueryService requisitionVoucherRelationQueryService;

    public RequisitionVoucherRelationResource(RequisitionVoucherRelationService requisitionVoucherRelationService, RequisitionVoucherRelationQueryService requisitionVoucherRelationQueryService) {
        this.requisitionVoucherRelationService = requisitionVoucherRelationService;
        this.requisitionVoucherRelationQueryService = requisitionVoucherRelationQueryService;
    }

    /**
     * POST  /requisition-voucher-relations : Create a new requisitionVoucherRelation.
     *
     * @param requisitionVoucherRelationDTO the requisitionVoucherRelationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requisitionVoucherRelationDTO, or with status 400 (Bad Request) if the requisitionVoucherRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requisition-voucher-relations")
    public ResponseEntity<RequisitionVoucherRelationDTO> createRequisitionVoucherRelation(@RequestBody RequisitionVoucherRelationDTO requisitionVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to save RequisitionVoucherRelation : {}", requisitionVoucherRelationDTO);
        if (requisitionVoucherRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisitionVoucherRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitionVoucherRelationDTO result = requisitionVoucherRelationService.save(requisitionVoucherRelationDTO);
        return ResponseEntity.created(new URI("/api/requisition-voucher-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requisition-voucher-relations : Updates an existing requisitionVoucherRelation.
     *
     * @param requisitionVoucherRelationDTO the requisitionVoucherRelationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requisitionVoucherRelationDTO,
     * or with status 400 (Bad Request) if the requisitionVoucherRelationDTO is not valid,
     * or with status 500 (Internal Server Error) if the requisitionVoucherRelationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requisition-voucher-relations")
    public ResponseEntity<RequisitionVoucherRelationDTO> updateRequisitionVoucherRelation(@RequestBody RequisitionVoucherRelationDTO requisitionVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to update RequisitionVoucherRelation : {}", requisitionVoucherRelationDTO);
        if (requisitionVoucherRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitionVoucherRelationDTO result = requisitionVoucherRelationService.save(requisitionVoucherRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requisitionVoucherRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requisition-voucher-relations : get all the requisitionVoucherRelations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of requisitionVoucherRelations in body
     */
    @GetMapping("/requisition-voucher-relations")
    public ResponseEntity<List<RequisitionVoucherRelationDTO>> getAllRequisitionVoucherRelations(RequisitionVoucherRelationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RequisitionVoucherRelations by criteria: {}", criteria);
        Page<RequisitionVoucherRelationDTO> page = requisitionVoucherRelationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requisition-voucher-relations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /requisition-voucher-relations/count : count all the requisitionVoucherRelations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/requisition-voucher-relations/count")
    public ResponseEntity<Long> countRequisitionVoucherRelations(RequisitionVoucherRelationCriteria criteria) {
        log.debug("REST request to count RequisitionVoucherRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(requisitionVoucherRelationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /requisition-voucher-relations/:id : get the "id" requisitionVoucherRelation.
     *
     * @param id the id of the requisitionVoucherRelationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requisitionVoucherRelationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/requisition-voucher-relations/{id}")
    public ResponseEntity<RequisitionVoucherRelationDTO> getRequisitionVoucherRelation(@PathVariable Long id) {
        log.debug("REST request to get RequisitionVoucherRelation : {}", id);
        Optional<RequisitionVoucherRelationDTO> requisitionVoucherRelationDTO = requisitionVoucherRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisitionVoucherRelationDTO);
    }

    /**
     * DELETE  /requisition-voucher-relations/:id : delete the "id" requisitionVoucherRelation.
     *
     * @param id the id of the requisitionVoucherRelationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requisition-voucher-relations/{id}")
    public ResponseEntity<Void> deleteRequisitionVoucherRelation(@PathVariable Long id) {
        log.debug("REST request to delete RequisitionVoucherRelation : {}", id);
        requisitionVoucherRelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/requisition-voucher-relations?query=:query : search for the requisitionVoucherRelation corresponding
     * to the query.
     *
     * @param query the query of the requisitionVoucherRelation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/requisition-voucher-relations")
    public ResponseEntity<List<RequisitionVoucherRelationDTO>> searchRequisitionVoucherRelations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequisitionVoucherRelations for query {}", query);
        Page<RequisitionVoucherRelationDTO> page = requisitionVoucherRelationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/requisition-voucher-relations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
