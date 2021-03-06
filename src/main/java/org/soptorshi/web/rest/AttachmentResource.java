package org.soptorshi.web.rest;
import org.soptorshi.service.AttachmentService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.AttachmentDTO;
import org.soptorshi.service.dto.AttachmentCriteria;
import org.soptorshi.service.AttachmentQueryService;
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
 * REST controller for managing Attachment.
 */
@RestController
@RequestMapping("/api")
public class AttachmentResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);

    private static final String ENTITY_NAME = "attachment";

    private final AttachmentService attachmentService;

    private final AttachmentQueryService attachmentQueryService;

    public AttachmentResource(AttachmentService attachmentService, AttachmentQueryService attachmentQueryService) {
        this.attachmentService = attachmentService;
        this.attachmentQueryService = attachmentQueryService;
    }

    /**
     * POST  /attachments : Create a new attachment.
     *
     * @param attachmentDTO the attachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attachmentDTO, or with status 400 (Bad Request) if the attachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attachments")
    public ResponseEntity<AttachmentDTO> createAttachment(@RequestBody AttachmentDTO attachmentDTO) throws URISyntaxException {
        log.debug("REST request to save Attachment : {}", attachmentDTO);
        if (attachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentDTO result = attachmentService.save(attachmentDTO);
        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attachments : Updates an existing attachment.
     *
     * @param attachmentDTO the attachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attachmentDTO,
     * or with status 400 (Bad Request) if the attachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the attachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attachments")
    public ResponseEntity<AttachmentDTO> updateAttachment(@RequestBody AttachmentDTO attachmentDTO) throws URISyntaxException {
        log.debug("REST request to update Attachment : {}", attachmentDTO);
        if (attachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttachmentDTO result = attachmentService.save(attachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attachments : get all the attachments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of attachments in body
     */
    @GetMapping("/attachments")
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments(AttachmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Attachments by criteria: {}", criteria);
        Page<AttachmentDTO> page = attachmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attachments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /attachments/count : count all the attachments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/attachments/count")
    public ResponseEntity<Long> countAttachments(AttachmentCriteria criteria) {
        log.debug("REST request to count Attachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(attachmentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /attachments/:id : get the "id" attachment.
     *
     * @param id the id of the attachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attachments/{id}")
    public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable Long id) {
        log.debug("REST request to get Attachment : {}", id);
        Optional<AttachmentDTO> attachmentDTO = attachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentDTO);
    }

    /**
     * DELETE  /attachments/:id : delete the "id" attachment.
     *
     * @param id the id of the attachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attachments/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        log.debug("REST request to delete Attachment : {}", id);
        attachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/attachments?query=:query : search for the attachment corresponding
     * to the query.
     *
     * @param query the query of the attachment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/attachments")
    public ResponseEntity<List<AttachmentDTO>> searchAttachments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Attachments for query {}", query);
        Page<AttachmentDTO> page = attachmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/attachments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
