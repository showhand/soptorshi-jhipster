package org.soptorshi.web.rest;
import org.soptorshi.service.DepartmentHeadService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.DepartmentHeadDTO;
import org.soptorshi.service.dto.DepartmentHeadCriteria;
import org.soptorshi.service.DepartmentHeadQueryService;
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
 * REST controller for managing DepartmentHead.
 */
@RestController
@RequestMapping("/api")
public class DepartmentHeadResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentHeadResource.class);

    private static final String ENTITY_NAME = "departmentHead";

    private final DepartmentHeadService departmentHeadService;

    private final DepartmentHeadQueryService departmentHeadQueryService;

    public DepartmentHeadResource(DepartmentHeadService departmentHeadService, DepartmentHeadQueryService departmentHeadQueryService) {
        this.departmentHeadService = departmentHeadService;
        this.departmentHeadQueryService = departmentHeadQueryService;
    }

    /**
     * POST  /department-heads : Create a new departmentHead.
     *
     * @param departmentHeadDTO the departmentHeadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departmentHeadDTO, or with status 400 (Bad Request) if the departmentHead has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/department-heads")
    public ResponseEntity<DepartmentHeadDTO> createDepartmentHead(@RequestBody DepartmentHeadDTO departmentHeadDTO) throws URISyntaxException {
        log.debug("REST request to save DepartmentHead : {}", departmentHeadDTO);
        if (departmentHeadDTO.getId() != null) {
            throw new BadRequestAlertException("A new departmentHead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentHeadDTO result = departmentHeadService.save(departmentHeadDTO);
        return ResponseEntity.created(new URI("/api/department-heads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /department-heads : Updates an existing departmentHead.
     *
     * @param departmentHeadDTO the departmentHeadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departmentHeadDTO,
     * or with status 400 (Bad Request) if the departmentHeadDTO is not valid,
     * or with status 500 (Internal Server Error) if the departmentHeadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/department-heads")
    public ResponseEntity<DepartmentHeadDTO> updateDepartmentHead(@RequestBody DepartmentHeadDTO departmentHeadDTO) throws URISyntaxException {
        log.debug("REST request to update DepartmentHead : {}", departmentHeadDTO);
        if (departmentHeadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartmentHeadDTO result = departmentHeadService.save(departmentHeadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departmentHeadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /department-heads : get all the departmentHeads.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of departmentHeads in body
     */
    @GetMapping("/department-heads")
    public ResponseEntity<List<DepartmentHeadDTO>> getAllDepartmentHeads(DepartmentHeadCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DepartmentHeads by criteria: {}", criteria);
        Page<DepartmentHeadDTO> page = departmentHeadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/department-heads");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /department-heads/count : count all the departmentHeads.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/department-heads/count")
    public ResponseEntity<Long> countDepartmentHeads(DepartmentHeadCriteria criteria) {
        log.debug("REST request to count DepartmentHeads by criteria: {}", criteria);
        return ResponseEntity.ok().body(departmentHeadQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /department-heads/:id : get the "id" departmentHead.
     *
     * @param id the id of the departmentHeadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departmentHeadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/department-heads/{id}")
    public ResponseEntity<DepartmentHeadDTO> getDepartmentHead(@PathVariable Long id) {
        log.debug("REST request to get DepartmentHead : {}", id);
        Optional<DepartmentHeadDTO> departmentHeadDTO = departmentHeadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departmentHeadDTO);
    }

    /**
     * DELETE  /department-heads/:id : delete the "id" departmentHead.
     *
     * @param id the id of the departmentHeadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/department-heads/{id}")
    public ResponseEntity<Void> deleteDepartmentHead(@PathVariable Long id) {
        log.debug("REST request to delete DepartmentHead : {}", id);
        departmentHeadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/department-heads?query=:query : search for the departmentHead corresponding
     * to the query.
     *
     * @param query the query of the departmentHead search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/department-heads")
    public ResponseEntity<List<DepartmentHeadDTO>> searchDepartmentHeads(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DepartmentHeads for query {}", query);
        Page<DepartmentHeadDTO> page = departmentHeadService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/department-heads");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
