package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialAttachmentQueryService;
import org.soptorshi.service.CommercialAttachmentService;
import org.soptorshi.service.dto.CommercialAttachmentCriteria;
import org.soptorshi.service.dto.CommercialAttachmentDTO;
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
 * REST controller for managing CommercialAttachment.
 */
@RestController
@RequestMapping("/api")
public class CommercialAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(CommercialAttachmentResource.class);

    private static final String ENTITY_NAME = "commercialAttachment";

    private final CommercialAttachmentService commercialAttachmentService;

    private final CommercialAttachmentQueryService commercialAttachmentQueryService;

    public CommercialAttachmentResource(CommercialAttachmentService commercialAttachmentService, CommercialAttachmentQueryService commercialAttachmentQueryService) {
        this.commercialAttachmentService = commercialAttachmentService;
        this.commercialAttachmentQueryService = commercialAttachmentQueryService;
    }

    /**
     * POST  /commercial-attachments : Create a new commercialAttachment.
     *
     * @param commercialAttachmentDTO the commercialAttachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialAttachmentDTO, or with status 400 (Bad Request) if the commercialAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-attachments")
    public ResponseEntity<CommercialAttachmentDTO> createCommercialAttachment(@Valid @RequestBody CommercialAttachmentDTO commercialAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialAttachment : {}", commercialAttachmentDTO);
        if (commercialAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialAttachmentDTO result = commercialAttachmentService.save(commercialAttachmentDTO);
        return ResponseEntity.created(new URI("/api/commercial-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-attachments : Updates an existing commercialAttachment.
     *
     * @param commercialAttachmentDTO the commercialAttachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialAttachmentDTO,
     * or with status 400 (Bad Request) if the commercialAttachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialAttachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-attachments")
    public ResponseEntity<CommercialAttachmentDTO> updateCommercialAttachment(@Valid @RequestBody CommercialAttachmentDTO commercialAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialAttachment : {}", commercialAttachmentDTO);
        if (commercialAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialAttachmentDTO result = commercialAttachmentService.save(commercialAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-attachments : get all the commercialAttachments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialAttachments in body
     */
    @GetMapping("/commercial-attachments")
    public ResponseEntity<List<CommercialAttachmentDTO>> getAllCommercialAttachments(CommercialAttachmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialAttachments by criteria: {}", criteria);
        Page<CommercialAttachmentDTO> page = commercialAttachmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-attachments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-attachments/count : count all the commercialAttachments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-attachments/count")
    public ResponseEntity<Long> countCommercialAttachments(CommercialAttachmentCriteria criteria) {
        log.debug("REST request to count CommercialAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialAttachmentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-attachments/:id : get the "id" commercialAttachment.
     *
     * @param id the id of the commercialAttachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialAttachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-attachments/{id}")
    public ResponseEntity<CommercialAttachmentDTO> getCommercialAttachment(@PathVariable Long id) {
        log.debug("REST request to get CommercialAttachment : {}", id);
        Optional<CommercialAttachmentDTO> commercialAttachmentDTO = commercialAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialAttachmentDTO);
    }

    /**
     * DELETE  /commercial-attachments/:id : delete the "id" commercialAttachment.
     *
     * @param id the id of the commercialAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-attachments/{id}")
    public ResponseEntity<Void> deleteCommercialAttachment(@PathVariable Long id) {
        log.debug("REST request to delete CommercialAttachment : {}", id);
        commercialAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-attachments?query=:query : search for the commercialAttachment corresponding
     * to the query.
     *
     * @param query the query of the commercialAttachment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-attachments")
    public ResponseEntity<List<CommercialAttachmentDTO>> searchCommercialAttachments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialAttachments for query {}", query);
        Page<CommercialAttachmentDTO> page = commercialAttachmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-attachments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
