package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.AttendanceQueryService;
import org.soptorshi.service.AttendanceService;
import org.soptorshi.service.dto.AttendanceCriteria;
import org.soptorshi.service.dto.AttendanceDTO;
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
 * REST controller for managing Attendance.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

    private static final String ENTITY_NAME = "attendance";

    private final AttendanceService attendanceService;

    private final AttendanceQueryService attendanceQueryService;

    public AttendanceResource(AttendanceService attendanceService, AttendanceQueryService attendanceQueryService) {
        this.attendanceService = attendanceService;
        this.attendanceQueryService = attendanceQueryService;
    }

    /**
     * POST  /attendances : Create a new attendance.
     *
     * @param attendanceDTO the attendanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendanceDTO, or with status 400 (Bad Request) if the attendance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attendances")
    public ResponseEntity<AttendanceDTO> createAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
        log.debug("REST request to save Attendance : {}", attendanceDTO);
        if (attendanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceDTO result = attendanceService.save(attendanceDTO);
        return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attendances : Updates an existing attendance.
     *
     * @param attendanceDTO the attendanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendanceDTO,
     * or with status 400 (Bad Request) if the attendanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the attendanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attendances")
    public ResponseEntity<AttendanceDTO> updateAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
        log.debug("REST request to update Attendance : {}", attendanceDTO);
        if (attendanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendanceDTO result = attendanceService.save(attendanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendances : get all the attendances.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of attendances in body
     */
    @GetMapping("/attendances")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendances(AttendanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Attendances by criteria: {}", criteria);
        Page<AttendanceDTO> page = attendanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attendances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /attendances/count : count all the attendances.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/attendances/count")
    public ResponseEntity<Long> countAttendances(AttendanceCriteria criteria) {
        log.debug("REST request to count Attendances by criteria: {}", criteria);
        return ResponseEntity.ok().body(attendanceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /attendances/:id : get the "id" attendance.
     *
     * @param id the id of the attendanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attendances/{id}")
    public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable Long id) {
        log.debug("REST request to get Attendance : {}", id);
        Optional<AttendanceDTO> attendanceDTO = attendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceDTO);
    }

    /**
     * DELETE  /attendances/:id : delete the "id" attendance.
     *
     * @param id the id of the attendanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendances/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        attendanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/attendances?query=:query : search for the attendance corresponding
     * to the query.
     *
     * @param query the query of the attendance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/attendances")
    public ResponseEntity<List<AttendanceDTO>> searchAttendances(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Attendances for query {}", query);
        Page<AttendanceDTO> page = attendanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/attendances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
