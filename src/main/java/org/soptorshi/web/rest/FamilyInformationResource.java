package org.soptorshi.web.rest;
import org.soptorshi.service.FamilyInformationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.FamilyInformationDTO;
import org.soptorshi.service.dto.FamilyInformationCriteria;
import org.soptorshi.service.FamilyInformationQueryService;
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
 * REST controller for managing FamilyInformation.
 */
@RestController
@RequestMapping("/api")
public class FamilyInformationResource {

    private final Logger log = LoggerFactory.getLogger(FamilyInformationResource.class);

    private static final String ENTITY_NAME = "familyInformation";

    private final FamilyInformationService familyInformationService;

    private final FamilyInformationQueryService familyInformationQueryService;

    public FamilyInformationResource(FamilyInformationService familyInformationService, FamilyInformationQueryService familyInformationQueryService) {
        this.familyInformationService = familyInformationService;
        this.familyInformationQueryService = familyInformationQueryService;
    }

    /**
     * POST  /family-informations : Create a new familyInformation.
     *
     * @param familyInformationDTO the familyInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familyInformationDTO, or with status 400 (Bad Request) if the familyInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/family-informations")
    public ResponseEntity<FamilyInformationDTO> createFamilyInformation(@RequestBody FamilyInformationDTO familyInformationDTO) throws URISyntaxException {
        log.debug("REST request to save FamilyInformation : {}", familyInformationDTO);
        if (familyInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new familyInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyInformationDTO result = familyInformationService.save(familyInformationDTO);
        return ResponseEntity.created(new URI("/api/family-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /family-informations : Updates an existing familyInformation.
     *
     * @param familyInformationDTO the familyInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familyInformationDTO,
     * or with status 400 (Bad Request) if the familyInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the familyInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/family-informations")
    public ResponseEntity<FamilyInformationDTO> updateFamilyInformation(@RequestBody FamilyInformationDTO familyInformationDTO) throws URISyntaxException {
        log.debug("REST request to update FamilyInformation : {}", familyInformationDTO);
        if (familyInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyInformationDTO result = familyInformationService.save(familyInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familyInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /family-informations : get all the familyInformations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of familyInformations in body
     */
    @GetMapping("/family-informations")
    public ResponseEntity<List<FamilyInformationDTO>> getAllFamilyInformations(FamilyInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FamilyInformations by criteria: {}", criteria);
        Page<FamilyInformationDTO> page = familyInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/family-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /family-informations/count : count all the familyInformations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/family-informations/count")
    public ResponseEntity<Long> countFamilyInformations(FamilyInformationCriteria criteria) {
        log.debug("REST request to count FamilyInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(familyInformationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /family-informations/:id : get the "id" familyInformation.
     *
     * @param id the id of the familyInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familyInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/family-informations/{id}")
    public ResponseEntity<FamilyInformationDTO> getFamilyInformation(@PathVariable Long id) {
        log.debug("REST request to get FamilyInformation : {}", id);
        Optional<FamilyInformationDTO> familyInformationDTO = familyInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyInformationDTO);
    }

    /**
     * DELETE  /family-informations/:id : delete the "id" familyInformation.
     *
     * @param id the id of the familyInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/family-informations/{id}")
    public ResponseEntity<Void> deleteFamilyInformation(@PathVariable Long id) {
        log.debug("REST request to delete FamilyInformation : {}", id);
        familyInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/family-informations?query=:query : search for the familyInformation corresponding
     * to the query.
     *
     * @param query the query of the familyInformation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/family-informations")
    public ResponseEntity<List<FamilyInformationDTO>> searchFamilyInformations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FamilyInformations for query {}", query);
        Page<FamilyInformationDTO> page = familyInformationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/family-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
