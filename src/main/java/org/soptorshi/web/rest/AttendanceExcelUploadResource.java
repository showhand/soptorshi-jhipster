package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.AttendanceExcelUploadQueryService;
import org.soptorshi.service.AttendanceExcelUploadService;
import org.soptorshi.service.dto.AttendanceExcelUploadCriteria;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
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
 * REST controller for managing AttendanceExcelUpload.
 */
@RestController
@RequestMapping("/api")
public class AttendanceExcelUploadResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadResource.class);

    private static final String ENTITY_NAME = "attendanceExcelUpload";

    private final AttendanceExcelUploadService attendanceExcelUploadService;

    private final AttendanceExcelUploadQueryService attendanceExcelUploadQueryService;

    public AttendanceExcelUploadResource(AttendanceExcelUploadService attendanceExcelUploadService, AttendanceExcelUploadQueryService attendanceExcelUploadQueryService) {
        this.attendanceExcelUploadService = attendanceExcelUploadService;
        this.attendanceExcelUploadQueryService = attendanceExcelUploadQueryService;
    }

    /**
     * POST  /attendance-excel-uploads : Create a new attendanceExcelUpload.
     *
     * @param attendanceExcelUploadDTO the attendanceExcelUploadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendanceExcelUploadDTO, or with status 400 (Bad Request) if the attendanceExcelUpload has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attendance-excel-uploads")
    public ResponseEntity<AttendanceExcelUploadDTO> createAttendanceExcelUpload(@Valid @RequestBody AttendanceExcelUploadDTO attendanceExcelUploadDTO) throws URISyntaxException {
        log.debug("REST request to save AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        if (attendanceExcelUploadDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendanceExcelUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceExcelUploadDTO result = attendanceExcelUploadService.save(attendanceExcelUploadDTO);
        return ResponseEntity.created(new URI("/api/attendance-excel-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attendance-excel-uploads : Updates an existing attendanceExcelUpload.
     *
     * @param attendanceExcelUploadDTO the attendanceExcelUploadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendanceExcelUploadDTO,
     * or with status 400 (Bad Request) if the attendanceExcelUploadDTO is not valid,
     * or with status 500 (Internal Server Error) if the attendanceExcelUploadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attendance-excel-uploads")
    public ResponseEntity<AttendanceExcelUploadDTO> updateAttendanceExcelUpload(@Valid @RequestBody AttendanceExcelUploadDTO attendanceExcelUploadDTO) throws URISyntaxException {
        log.debug("REST request to update AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        if (attendanceExcelUploadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendanceExcelUploadDTO result = attendanceExcelUploadService.save(attendanceExcelUploadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendanceExcelUploadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendance-excel-uploads : get all the attendanceExcelUploads.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of attendanceExcelUploads in body
     */
    @GetMapping("/attendance-excel-uploads")
    public ResponseEntity<List<AttendanceExcelUploadDTO>> getAllAttendanceExcelUploads(AttendanceExcelUploadCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AttendanceExcelUploads by criteria: {}", criteria);
        Page<AttendanceExcelUploadDTO> page = attendanceExcelUploadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attendance-excel-uploads");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /attendance-excel-uploads/count : count all the attendanceExcelUploads.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/attendance-excel-uploads/count")
    public ResponseEntity<Long> countAttendanceExcelUploads(AttendanceExcelUploadCriteria criteria) {
        log.debug("REST request to count AttendanceExcelUploads by criteria: {}", criteria);
        return ResponseEntity.ok().body(attendanceExcelUploadQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /attendance-excel-uploads/:id : get the "id" attendanceExcelUpload.
     *
     * @param id the id of the attendanceExcelUploadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendanceExcelUploadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attendance-excel-uploads/{id}")
    public ResponseEntity<AttendanceExcelUploadDTO> getAttendanceExcelUpload(@PathVariable Long id) {
        log.debug("REST request to get AttendanceExcelUpload : {}", id);
        Optional<AttendanceExcelUploadDTO> attendanceExcelUploadDTO = attendanceExcelUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceExcelUploadDTO);
    }

    /**
     * DELETE  /attendance-excel-uploads/:id : delete the "id" attendanceExcelUpload.
     *
     * @param id the id of the attendanceExcelUploadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendance-excel-uploads/{id}")
    public ResponseEntity<Void> deleteAttendanceExcelUpload(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceExcelUpload : {}", id);
        attendanceExcelUploadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/attendance-excel-uploads?query=:query : search for the attendanceExcelUpload corresponding
     * to the query.
     *
     * @param query the query of the attendanceExcelUpload search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/attendance-excel-uploads")
    public ResponseEntity<List<AttendanceExcelUploadDTO>> searchAttendanceExcelUploads(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttendanceExcelUploads for query {}", query);
        Page<AttendanceExcelUploadDTO> page = attendanceExcelUploadService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/attendance-excel-uploads");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
