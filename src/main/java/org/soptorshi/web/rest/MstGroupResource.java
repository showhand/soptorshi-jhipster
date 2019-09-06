package org.soptorshi.web.rest;
import org.soptorshi.service.MstGroupService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.MstGroupDTO;
import org.soptorshi.service.dto.MstGroupCriteria;
import org.soptorshi.service.MstGroupQueryService;
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
 * REST controller for managing MstGroup.
 */
@RestController
@RequestMapping("/api")
public class MstGroupResource {

    private final Logger log = LoggerFactory.getLogger(MstGroupResource.class);

    private static final String ENTITY_NAME = "mstGroup";

    private final MstGroupService mstGroupService;

    private final MstGroupQueryService mstGroupQueryService;

    public MstGroupResource(MstGroupService mstGroupService, MstGroupQueryService mstGroupQueryService) {
        this.mstGroupService = mstGroupService;
        this.mstGroupQueryService = mstGroupQueryService;
    }

    /**
     * POST  /mst-groups : Create a new mstGroup.
     *
     * @param mstGroupDTO the mstGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mstGroupDTO, or with status 400 (Bad Request) if the mstGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mst-groups")
    public ResponseEntity<MstGroupDTO> createMstGroup(@RequestBody MstGroupDTO mstGroupDTO) throws URISyntaxException {
        log.debug("REST request to save MstGroup : {}", mstGroupDTO);
        if (mstGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new mstGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MstGroupDTO result = mstGroupService.save(mstGroupDTO);
        return ResponseEntity.created(new URI("/api/mst-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mst-groups : Updates an existing mstGroup.
     *
     * @param mstGroupDTO the mstGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mstGroupDTO,
     * or with status 400 (Bad Request) if the mstGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the mstGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mst-groups")
    public ResponseEntity<MstGroupDTO> updateMstGroup(@RequestBody MstGroupDTO mstGroupDTO) throws URISyntaxException {
        log.debug("REST request to update MstGroup : {}", mstGroupDTO);
        if (mstGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MstGroupDTO result = mstGroupService.save(mstGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mstGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mst-groups : get all the mstGroups.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mstGroups in body
     */
    @GetMapping("/mst-groups")
    public ResponseEntity<List<MstGroupDTO>> getAllMstGroups(MstGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MstGroups by criteria: {}", criteria);
        Page<MstGroupDTO> page = mstGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mst-groups");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /mst-groups/count : count all the mstGroups.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/mst-groups/count")
    public ResponseEntity<Long> countMstGroups(MstGroupCriteria criteria) {
        log.debug("REST request to count MstGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(mstGroupQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /mst-groups/:id : get the "id" mstGroup.
     *
     * @param id the id of the mstGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mstGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mst-groups/{id}")
    public ResponseEntity<MstGroupDTO> getMstGroup(@PathVariable Long id) {
        log.debug("REST request to get MstGroup : {}", id);
        Optional<MstGroupDTO> mstGroupDTO = mstGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mstGroupDTO);
    }

    /**
     * DELETE  /mst-groups/:id : delete the "id" mstGroup.
     *
     * @param id the id of the mstGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mst-groups/{id}")
    public ResponseEntity<Void> deleteMstGroup(@PathVariable Long id) {
        log.debug("REST request to delete MstGroup : {}", id);
        mstGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mst-groups?query=:query : search for the mstGroup corresponding
     * to the query.
     *
     * @param query the query of the mstGroup search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mst-groups")
    public ResponseEntity<List<MstGroupDTO>> searchMstGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MstGroups for query {}", query);
        Page<MstGroupDTO> page = mstGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mst-groups");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
