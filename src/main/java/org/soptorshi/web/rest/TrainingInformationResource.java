package org.soptorshi.web.rest;
import org.soptorshi.service.TrainingInformationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.TrainingInformationDTO;
import org.soptorshi.service.dto.TrainingInformationCriteria;
import org.soptorshi.service.TrainingInformationQueryService;
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
 * REST controller for managing TrainingInformation.
 */
@RestController
@RequestMapping("/api")
public class TrainingInformationResource {

    private final Logger log = LoggerFactory.getLogger(TrainingInformationResource.class);

    private static final String ENTITY_NAME = "trainingInformation";

    private final TrainingInformationService trainingInformationService;

    private final TrainingInformationQueryService trainingInformationQueryService;

    public TrainingInformationResource(TrainingInformationService trainingInformationService, TrainingInformationQueryService trainingInformationQueryService) {
        this.trainingInformationService = trainingInformationService;
        this.trainingInformationQueryService = trainingInformationQueryService;
    }

    /**
     * POST  /training-informations : Create a new trainingInformation.
     *
     * @param trainingInformationDTO the trainingInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trainingInformationDTO, or with status 400 (Bad Request) if the trainingInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/training-informations")
    public ResponseEntity<TrainingInformationDTO> createTrainingInformation(@RequestBody TrainingInformationDTO trainingInformationDTO) throws URISyntaxException {
        log.debug("REST request to save TrainingInformation : {}", trainingInformationDTO);
        if (trainingInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainingInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingInformationDTO result = trainingInformationService.save(trainingInformationDTO);
        return ResponseEntity.created(new URI("/api/training-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /training-informations : Updates an existing trainingInformation.
     *
     * @param trainingInformationDTO the trainingInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trainingInformationDTO,
     * or with status 400 (Bad Request) if the trainingInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the trainingInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/training-informations")
    public ResponseEntity<TrainingInformationDTO> updateTrainingInformation(@RequestBody TrainingInformationDTO trainingInformationDTO) throws URISyntaxException {
        log.debug("REST request to update TrainingInformation : {}", trainingInformationDTO);
        if (trainingInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingInformationDTO result = trainingInformationService.save(trainingInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trainingInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /training-informations : get all the trainingInformations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of trainingInformations in body
     */
    @GetMapping("/training-informations")
    public ResponseEntity<List<TrainingInformationDTO>> getAllTrainingInformations(TrainingInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TrainingInformations by criteria: {}", criteria);
        Page<TrainingInformationDTO> page = trainingInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/training-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /training-informations/count : count all the trainingInformations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/training-informations/count")
    public ResponseEntity<Long> countTrainingInformations(TrainingInformationCriteria criteria) {
        log.debug("REST request to count TrainingInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(trainingInformationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /training-informations/:id : get the "id" trainingInformation.
     *
     * @param id the id of the trainingInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trainingInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/training-informations/{id}")
    public ResponseEntity<TrainingInformationDTO> getTrainingInformation(@PathVariable Long id) {
        log.debug("REST request to get TrainingInformation : {}", id);
        Optional<TrainingInformationDTO> trainingInformationDTO = trainingInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingInformationDTO);
    }

    /**
     * DELETE  /training-informations/:id : delete the "id" trainingInformation.
     *
     * @param id the id of the trainingInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/training-informations/{id}")
    public ResponseEntity<Void> deleteTrainingInformation(@PathVariable Long id) {
        log.debug("REST request to delete TrainingInformation : {}", id);
        trainingInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/training-informations?query=:query : search for the trainingInformation corresponding
     * to the query.
     *
     * @param query the query of the trainingInformation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/training-informations")
    public ResponseEntity<List<TrainingInformationDTO>> searchTrainingInformations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TrainingInformations for query {}", query);
        Page<TrainingInformationDTO> page = trainingInformationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/training-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
