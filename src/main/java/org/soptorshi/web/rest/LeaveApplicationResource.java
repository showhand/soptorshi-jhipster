package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveApplicationQueryService;
import org.soptorshi.service.LeaveApplicationService;
import org.soptorshi.service.dto.LeaveApplicationCriteria;
import org.soptorshi.service.dto.LeaveApplicationDTO;
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
 * REST controller for managing LeaveApplication.
 */
@RestController
@RequestMapping("/api")
public class LeaveApplicationResource {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationResource.class);

    private static final String ENTITY_NAME = "leaveApplication";

    private final LeaveApplicationService leaveApplicationService;

    private final LeaveApplicationQueryService leaveApplicationQueryService;

    public LeaveApplicationResource(LeaveApplicationService leaveApplicationService, LeaveApplicationQueryService leaveApplicationQueryService) {
        this.leaveApplicationService = leaveApplicationService;
        this.leaveApplicationQueryService = leaveApplicationQueryService;
    }

    /**
     * POST  /leave-applications : Create a new leaveApplication.
     *
     * @param leaveApplicationDTO the leaveApplicationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leaveApplicationDTO, or with status 400 (Bad Request) if the leaveApplication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leave-applications")
    public ResponseEntity<LeaveApplicationDTO> createLeaveApplication(@Valid @RequestBody LeaveApplicationDTO leaveApplicationDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveApplication : {}", leaveApplicationDTO);
        if (leaveApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveApplicationDTO result = leaveApplicationService.save(leaveApplicationDTO);
        return ResponseEntity.created(new URI("/api/leave-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leave-applications : Updates an existing leaveApplication.
     *
     * @param leaveApplicationDTO the leaveApplicationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leaveApplicationDTO,
     * or with status 400 (Bad Request) if the leaveApplicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the leaveApplicationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leave-applications")
    public ResponseEntity<LeaveApplicationDTO> updateLeaveApplication(@Valid @RequestBody LeaveApplicationDTO leaveApplicationDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveApplication : {}", leaveApplicationDTO);
        if (leaveApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveApplicationDTO result = leaveApplicationService.save(leaveApplicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leave-applications : get all the leaveApplications.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of leaveApplications in body
     */
    @GetMapping("/leave-applications")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllLeaveApplications(LeaveApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeaveApplications by criteria: {}", criteria);
        Page<LeaveApplicationDTO> page = leaveApplicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leave-applications");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /leave-applications/count : count all the leaveApplications.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/leave-applications/count")
    public ResponseEntity<Long> countLeaveApplications(LeaveApplicationCriteria criteria) {
        log.debug("REST request to count LeaveApplications by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveApplicationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /leave-applications/:id : get the "id" leaveApplication.
     *
     * @param id the id of the leaveApplicationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leaveApplicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leave-applications/{id}")
    public ResponseEntity<LeaveApplicationDTO> getLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to get LeaveApplication : {}", id);
        Optional<LeaveApplicationDTO> leaveApplicationDTO = leaveApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveApplicationDTO);
    }

    /**
     * DELETE  /leave-applications/:id : delete the "id" leaveApplication.
     *
     * @param id the id of the leaveApplicationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-applications/{id}")
    public ResponseEntity<Void> deleteLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to delete LeaveApplication : {}", id);
        leaveApplicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/leave-applications?query=:query : search for the leaveApplication corresponding
     * to the query.
     *
     * @param query the query of the leaveApplication search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/leave-applications")
    public ResponseEntity<List<LeaveApplicationDTO>> searchLeaveApplications(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LeaveApplications for query {}", query);
        Page<LeaveApplicationDTO> page = leaveApplicationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/leave-applications");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
