package org.soptorshi.web.rest;
import org.soptorshi.service.ConversionFactorService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ConversionFactorDTO;
import org.soptorshi.service.dto.ConversionFactorCriteria;
import org.soptorshi.service.ConversionFactorQueryService;
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
 * REST controller for managing ConversionFactor.
 */
@RestController
@RequestMapping("/api")
public class ConversionFactorResource {

    private final Logger log = LoggerFactory.getLogger(ConversionFactorResource.class);

    private static final String ENTITY_NAME = "conversionFactor";

    private final ConversionFactorService conversionFactorService;

    private final ConversionFactorQueryService conversionFactorQueryService;

    public ConversionFactorResource(ConversionFactorService conversionFactorService, ConversionFactorQueryService conversionFactorQueryService) {
        this.conversionFactorService = conversionFactorService;
        this.conversionFactorQueryService = conversionFactorQueryService;
    }

    /**
     * POST  /conversion-factors : Create a new conversionFactor.
     *
     * @param conversionFactorDTO the conversionFactorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conversionFactorDTO, or with status 400 (Bad Request) if the conversionFactor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conversion-factors")
    public ResponseEntity<ConversionFactorDTO> createConversionFactor(@RequestBody ConversionFactorDTO conversionFactorDTO) throws URISyntaxException {
        log.debug("REST request to save ConversionFactor : {}", conversionFactorDTO);
        if (conversionFactorDTO.getId() != null) {
            throw new BadRequestAlertException("A new conversionFactor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConversionFactorDTO result = conversionFactorService.save(conversionFactorDTO);
        return ResponseEntity.created(new URI("/api/conversion-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conversion-factors : Updates an existing conversionFactor.
     *
     * @param conversionFactorDTO the conversionFactorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conversionFactorDTO,
     * or with status 400 (Bad Request) if the conversionFactorDTO is not valid,
     * or with status 500 (Internal Server Error) if the conversionFactorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conversion-factors")
    public ResponseEntity<ConversionFactorDTO> updateConversionFactor(@RequestBody ConversionFactorDTO conversionFactorDTO) throws URISyntaxException {
        log.debug("REST request to update ConversionFactor : {}", conversionFactorDTO);
        if (conversionFactorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConversionFactorDTO result = conversionFactorService.save(conversionFactorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conversionFactorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conversion-factors : get all the conversionFactors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of conversionFactors in body
     */
    @GetMapping("/conversion-factors")
    public ResponseEntity<List<ConversionFactorDTO>> getAllConversionFactors(ConversionFactorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ConversionFactors by criteria: {}", criteria);
        Page<ConversionFactorDTO> page = conversionFactorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conversion-factors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /conversion-factors/count : count all the conversionFactors.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/conversion-factors/count")
    public ResponseEntity<Long> countConversionFactors(ConversionFactorCriteria criteria) {
        log.debug("REST request to count ConversionFactors by criteria: {}", criteria);
        return ResponseEntity.ok().body(conversionFactorQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /conversion-factors/:id : get the "id" conversionFactor.
     *
     * @param id the id of the conversionFactorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conversionFactorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/conversion-factors/{id}")
    public ResponseEntity<ConversionFactorDTO> getConversionFactor(@PathVariable Long id) {
        log.debug("REST request to get ConversionFactor : {}", id);
        Optional<ConversionFactorDTO> conversionFactorDTO = conversionFactorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conversionFactorDTO);
    }

    /**
     * DELETE  /conversion-factors/:id : delete the "id" conversionFactor.
     *
     * @param id the id of the conversionFactorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conversion-factors/{id}")
    public ResponseEntity<Void> deleteConversionFactor(@PathVariable Long id) {
        log.debug("REST request to delete ConversionFactor : {}", id);
        conversionFactorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/conversion-factors?query=:query : search for the conversionFactor corresponding
     * to the query.
     *
     * @param query the query of the conversionFactor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/conversion-factors")
    public ResponseEntity<List<ConversionFactorDTO>> searchConversionFactors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConversionFactors for query {}", query);
        Page<ConversionFactorDTO> page = conversionFactorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/conversion-factors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
