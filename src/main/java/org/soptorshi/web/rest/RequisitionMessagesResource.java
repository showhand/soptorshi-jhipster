package org.soptorshi.web.rest;
import org.soptorshi.service.RequisitionMessagesService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.RequisitionMessagesDTO;
import org.soptorshi.service.dto.RequisitionMessagesCriteria;
import org.soptorshi.service.RequisitionMessagesQueryService;
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
 * REST controller for managing RequisitionMessages.
 */
@RestController
@RequestMapping("/api")
public class RequisitionMessagesResource {

    private final Logger log = LoggerFactory.getLogger(RequisitionMessagesResource.class);

    private static final String ENTITY_NAME = "requisitionMessages";

    private final RequisitionMessagesService requisitionMessagesService;

    private final RequisitionMessagesQueryService requisitionMessagesQueryService;

    public RequisitionMessagesResource(RequisitionMessagesService requisitionMessagesService, RequisitionMessagesQueryService requisitionMessagesQueryService) {
        this.requisitionMessagesService = requisitionMessagesService;
        this.requisitionMessagesQueryService = requisitionMessagesQueryService;
    }

    /**
     * POST  /requisition-messages : Create a new requisitionMessages.
     *
     * @param requisitionMessagesDTO the requisitionMessagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requisitionMessagesDTO, or with status 400 (Bad Request) if the requisitionMessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requisition-messages")
    public ResponseEntity<RequisitionMessagesDTO> createRequisitionMessages(@RequestBody RequisitionMessagesDTO requisitionMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save RequisitionMessages : {}", requisitionMessagesDTO);
        if (requisitionMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisitionMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitionMessagesDTO result = requisitionMessagesService.save(requisitionMessagesDTO);
        return ResponseEntity.created(new URI("/api/requisition-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requisition-messages : Updates an existing requisitionMessages.
     *
     * @param requisitionMessagesDTO the requisitionMessagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requisitionMessagesDTO,
     * or with status 400 (Bad Request) if the requisitionMessagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the requisitionMessagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requisition-messages")
    public ResponseEntity<RequisitionMessagesDTO> updateRequisitionMessages(@RequestBody RequisitionMessagesDTO requisitionMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update RequisitionMessages : {}", requisitionMessagesDTO);
        if (requisitionMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitionMessagesDTO result = requisitionMessagesService.save(requisitionMessagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requisitionMessagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requisition-messages : get all the requisitionMessages.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of requisitionMessages in body
     */
    @GetMapping("/requisition-messages")
    public ResponseEntity<List<RequisitionMessagesDTO>> getAllRequisitionMessages(RequisitionMessagesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RequisitionMessages by criteria: {}", criteria);
        Page<RequisitionMessagesDTO> page = requisitionMessagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requisition-messages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /requisition-messages/count : count all the requisitionMessages.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/requisition-messages/count")
    public ResponseEntity<Long> countRequisitionMessages(RequisitionMessagesCriteria criteria) {
        log.debug("REST request to count RequisitionMessages by criteria: {}", criteria);
        return ResponseEntity.ok().body(requisitionMessagesQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /requisition-messages/:id : get the "id" requisitionMessages.
     *
     * @param id the id of the requisitionMessagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requisitionMessagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/requisition-messages/{id}")
    public ResponseEntity<RequisitionMessagesDTO> getRequisitionMessages(@PathVariable Long id) {
        log.debug("REST request to get RequisitionMessages : {}", id);
        Optional<RequisitionMessagesDTO> requisitionMessagesDTO = requisitionMessagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisitionMessagesDTO);
    }

    /**
     * DELETE  /requisition-messages/:id : delete the "id" requisitionMessages.
     *
     * @param id the id of the requisitionMessagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requisition-messages/{id}")
    public ResponseEntity<Void> deleteRequisitionMessages(@PathVariable Long id) {
        log.debug("REST request to delete RequisitionMessages : {}", id);
        requisitionMessagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/requisition-messages?query=:query : search for the requisitionMessages corresponding
     * to the query.
     *
     * @param query the query of the requisitionMessages search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/requisition-messages")
    public ResponseEntity<List<RequisitionMessagesDTO>> searchRequisitionMessages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequisitionMessages for query {}", query);
        Page<RequisitionMessagesDTO> page = requisitionMessagesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/requisition-messages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
