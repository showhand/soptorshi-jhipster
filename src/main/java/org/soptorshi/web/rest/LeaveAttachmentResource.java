package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveAttachmentQueryService;
import org.soptorshi.service.LeaveAttachmentService;
import org.soptorshi.service.dto.LeaveAttachmentCriteria;
import org.soptorshi.service.dto.LeaveAttachmentDTO;
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
 * REST controller for managing LeaveAttachment.
 */
@RestController
@RequestMapping("/api")
public class LeaveAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(LeaveAttachmentResource.class);

    private static final String ENTITY_NAME = "leaveAttachment";

    private final LeaveAttachmentService leaveAttachmentService;

    private final LeaveAttachmentQueryService leaveAttachmentQueryService;

    public LeaveAttachmentResource(LeaveAttachmentService leaveAttachmentService, LeaveAttachmentQueryService leaveAttachmentQueryService) {
        this.leaveAttachmentService = leaveAttachmentService;
        this.leaveAttachmentQueryService = leaveAttachmentQueryService;
    }

    /**
     * POST  /leave-attachments : Create a new leaveAttachment.
     *
     * @param leaveAttachmentDTO the leaveAttachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leaveAttachmentDTO, or with status 400 (Bad Request) if the leaveAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leave-attachments")
    public ResponseEntity<LeaveAttachmentDTO> createLeaveAttachment(@Valid @RequestBody LeaveAttachmentDTO leaveAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveAttachment : {}", leaveAttachmentDTO);
        if (leaveAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveAttachmentDTO result = leaveAttachmentService.save(leaveAttachmentDTO);
        return ResponseEntity.created(new URI("/api/leave-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leave-attachments : Updates an existing leaveAttachment.
     *
     * @param leaveAttachmentDTO the leaveAttachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leaveAttachmentDTO,
     * or with status 400 (Bad Request) if the leaveAttachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the leaveAttachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leave-attachments")
    public ResponseEntity<LeaveAttachmentDTO> updateLeaveAttachment(@Valid @RequestBody LeaveAttachmentDTO leaveAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveAttachment : {}", leaveAttachmentDTO);
        if (leaveAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveAttachmentDTO result = leaveAttachmentService.save(leaveAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leave-attachments : get all the leaveAttachments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of leaveAttachments in body
     */
    @GetMapping("/leave-attachments")
    public ResponseEntity<List<LeaveAttachmentDTO>> getAllLeaveAttachments(LeaveAttachmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeaveAttachments by criteria: {}", criteria);
        Page<LeaveAttachmentDTO> page = leaveAttachmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leave-attachments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /leave-attachments/count : count all the leaveAttachments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/leave-attachments/count")
    public ResponseEntity<Long> countLeaveAttachments(LeaveAttachmentCriteria criteria) {
        log.debug("REST request to count LeaveAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveAttachmentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /leave-attachments/:id : get the "id" leaveAttachment.
     *
     * @param id the id of the leaveAttachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leaveAttachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leave-attachments/{id}")
    public ResponseEntity<LeaveAttachmentDTO> getLeaveAttachment(@PathVariable Long id) {
        log.debug("REST request to get LeaveAttachment : {}", id);
        Optional<LeaveAttachmentDTO> leaveAttachmentDTO = leaveAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveAttachmentDTO);
    }

    /**
     * DELETE  /leave-attachments/:id : delete the "id" leaveAttachment.
     *
     * @param id the id of the leaveAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-attachments/{id}")
    public ResponseEntity<Void> deleteLeaveAttachment(@PathVariable Long id) {
        log.debug("REST request to delete LeaveAttachment : {}", id);
        leaveAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/leave-attachments?query=:query : search for the leaveAttachment corresponding
     * to the query.
     *
     * @param query the query of the leaveAttachment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/leave-attachments")
    public ResponseEntity<List<LeaveAttachmentDTO>> searchLeaveAttachments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LeaveAttachments for query {}", query);
        Page<LeaveAttachmentDTO> page = leaveAttachmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/leave-attachments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
