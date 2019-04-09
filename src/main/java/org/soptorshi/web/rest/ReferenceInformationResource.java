package org.soptorshi.web.rest;
import org.soptorshi.service.ReferenceInformationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ReferenceInformationDTO;
import org.soptorshi.service.dto.ReferenceInformationCriteria;
import org.soptorshi.service.ReferenceInformationQueryService;
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
 * REST controller for managing ReferenceInformation.
 */
@RestController
@RequestMapping("/api")
public class ReferenceInformationResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceInformationResource.class);

    private static final String ENTITY_NAME = "referenceInformation";

    private final ReferenceInformationService referenceInformationService;

    private final ReferenceInformationQueryService referenceInformationQueryService;

    public ReferenceInformationResource(ReferenceInformationService referenceInformationService, ReferenceInformationQueryService referenceInformationQueryService) {
        this.referenceInformationService = referenceInformationService;
        this.referenceInformationQueryService = referenceInformationQueryService;
    }

    /**
     * POST  /reference-informations : Create a new referenceInformation.
     *
     * @param referenceInformationDTO the referenceInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referenceInformationDTO, or with status 400 (Bad Request) if the referenceInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reference-informations")
    public ResponseEntity<ReferenceInformationDTO> createReferenceInformation(@RequestBody ReferenceInformationDTO referenceInformationDTO) throws URISyntaxException {
        log.debug("REST request to save ReferenceInformation : {}", referenceInformationDTO);
        if (referenceInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new referenceInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferenceInformationDTO result = referenceInformationService.save(referenceInformationDTO);
        return ResponseEntity.created(new URI("/api/reference-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reference-informations : Updates an existing referenceInformation.
     *
     * @param referenceInformationDTO the referenceInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referenceInformationDTO,
     * or with status 400 (Bad Request) if the referenceInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the referenceInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reference-informations")
    public ResponseEntity<ReferenceInformationDTO> updateReferenceInformation(@RequestBody ReferenceInformationDTO referenceInformationDTO) throws URISyntaxException {
        log.debug("REST request to update ReferenceInformation : {}", referenceInformationDTO);
        if (referenceInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReferenceInformationDTO result = referenceInformationService.save(referenceInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, referenceInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reference-informations : get all the referenceInformations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of referenceInformations in body
     */
    @GetMapping("/reference-informations")
    public ResponseEntity<List<ReferenceInformationDTO>> getAllReferenceInformations(ReferenceInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReferenceInformations by criteria: {}", criteria);
        Page<ReferenceInformationDTO> page = referenceInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reference-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /reference-informations/count : count all the referenceInformations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/reference-informations/count")
    public ResponseEntity<Long> countReferenceInformations(ReferenceInformationCriteria criteria) {
        log.debug("REST request to count ReferenceInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(referenceInformationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /reference-informations/:id : get the "id" referenceInformation.
     *
     * @param id the id of the referenceInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referenceInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reference-informations/{id}")
    public ResponseEntity<ReferenceInformationDTO> getReferenceInformation(@PathVariable Long id) {
        log.debug("REST request to get ReferenceInformation : {}", id);
        Optional<ReferenceInformationDTO> referenceInformationDTO = referenceInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referenceInformationDTO);
    }

    /**
     * DELETE  /reference-informations/:id : delete the "id" referenceInformation.
     *
     * @param id the id of the referenceInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reference-informations/{id}")
    public ResponseEntity<Void> deleteReferenceInformation(@PathVariable Long id) {
        log.debug("REST request to delete ReferenceInformation : {}", id);
        referenceInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reference-informations?query=:query : search for the referenceInformation corresponding
     * to the query.
     *
     * @param query the query of the referenceInformation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/reference-informations")
    public ResponseEntity<List<ReferenceInformationDTO>> searchReferenceInformations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReferenceInformations for query {}", query);
        Page<ReferenceInformationDTO> page = referenceInformationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/reference-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
