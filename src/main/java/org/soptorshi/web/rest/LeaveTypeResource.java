package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveTypeQueryService;
import org.soptorshi.service.LeaveTypeService;
import org.soptorshi.service.dto.LeaveTypeCriteria;
import org.soptorshi.service.dto.LeaveTypeDTO;
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
 * REST controller for managing LeaveType.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypeResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeResource.class);

    private static final String ENTITY_NAME = "leaveType";

    private final LeaveTypeService leaveTypeService;

    private final LeaveTypeQueryService leaveTypeQueryService;

    public LeaveTypeResource(LeaveTypeService leaveTypeService, LeaveTypeQueryService leaveTypeQueryService) {
        this.leaveTypeService = leaveTypeService;
        this.leaveTypeQueryService = leaveTypeQueryService;
    }

    /**
     * POST  /leave-types : Create a new leaveType.
     *
     * @param leaveTypeDTO the leaveTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leaveTypeDTO, or with status 400 (Bad Request) if the leaveType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leave-types")
    public ResponseEntity<LeaveTypeDTO> createLeaveType(@Valid @RequestBody LeaveTypeDTO leaveTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveType : {}", leaveTypeDTO);
        if (leaveTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypeDTO result = leaveTypeService.save(leaveTypeDTO);
        return ResponseEntity.created(new URI("/api/leave-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leave-types : Updates an existing leaveType.
     *
     * @param leaveTypeDTO the leaveTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leaveTypeDTO,
     * or with status 400 (Bad Request) if the leaveTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the leaveTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leave-types")
    public ResponseEntity<LeaveTypeDTO> updateLeaveType(@Valid @RequestBody LeaveTypeDTO leaveTypeDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveType : {}", leaveTypeDTO);
        if (leaveTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveTypeDTO result = leaveTypeService.save(leaveTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leave-types : get all the leaveTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of leaveTypes in body
     */
    @GetMapping("/leave-types")
    public ResponseEntity<List<LeaveTypeDTO>> getAllLeaveTypes(LeaveTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeaveTypes by criteria: {}", criteria);
        Page<LeaveTypeDTO> page = leaveTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leave-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /leave-types/count : count all the leaveTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/leave-types/count")
    public ResponseEntity<Long> countLeaveTypes(LeaveTypeCriteria criteria) {
        log.debug("REST request to count LeaveTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /leave-types/:id : get the "id" leaveType.
     *
     * @param id the id of the leaveTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leaveTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leave-types/{id}")
    public ResponseEntity<LeaveTypeDTO> getLeaveType(@PathVariable Long id) {
        log.debug("REST request to get LeaveType : {}", id);
        Optional<LeaveTypeDTO> leaveTypeDTO = leaveTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveTypeDTO);
    }

    /**
     * DELETE  /leave-types/:id : delete the "id" leaveType.
     *
     * @param id the id of the leaveTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-types/{id}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long id) {
        log.debug("REST request to delete LeaveType : {}", id);
        leaveTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/leave-types?query=:query : search for the leaveType corresponding
     * to the query.
     *
     * @param query the query of the leaveType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/leave-types")
    public ResponseEntity<List<LeaveTypeDTO>> searchLeaveTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LeaveTypes for query {}", query);
        Page<LeaveTypeDTO> page = leaveTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/leave-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
