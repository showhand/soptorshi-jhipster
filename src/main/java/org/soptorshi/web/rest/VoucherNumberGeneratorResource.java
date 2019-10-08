package org.soptorshi.web.rest;
import org.soptorshi.domain.VoucherNumberGenerator;
import org.soptorshi.repository.VoucherNumberGeneratorRepository;
import org.soptorshi.repository.search.VoucherNumberGeneratorSearchRepository;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing VoucherNumberGenerator.
 */
@RestController
@RequestMapping("/api")
public class VoucherNumberGeneratorResource {

    private final Logger log = LoggerFactory.getLogger(VoucherNumberGeneratorResource.class);

    private static final String ENTITY_NAME = "voucherNumberGenerator";

    private final VoucherNumberGeneratorRepository voucherNumberGeneratorRepository;

    private final VoucherNumberGeneratorSearchRepository voucherNumberGeneratorSearchRepository;

    public VoucherNumberGeneratorResource(VoucherNumberGeneratorRepository voucherNumberGeneratorRepository, VoucherNumberGeneratorSearchRepository voucherNumberGeneratorSearchRepository) {
        this.voucherNumberGeneratorRepository = voucherNumberGeneratorRepository;
        this.voucherNumberGeneratorSearchRepository = voucherNumberGeneratorSearchRepository;
    }

    /**
     * POST  /voucher-number-generators : Create a new voucherNumberGenerator.
     *
     * @param voucherNumberGenerator the voucherNumberGenerator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherNumberGenerator, or with status 400 (Bad Request) if the voucherNumberGenerator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-number-generators")
    public ResponseEntity<VoucherNumberGenerator> createVoucherNumberGenerator(@RequestBody VoucherNumberGenerator voucherNumberGenerator) throws URISyntaxException {
        log.debug("REST request to save VoucherNumberGenerator : {}", voucherNumberGenerator);
        if (voucherNumberGenerator.getId() != null) {
            throw new BadRequestAlertException("A new voucherNumberGenerator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherNumberGenerator result = voucherNumberGeneratorRepository.save(voucherNumberGenerator);
        voucherNumberGeneratorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/voucher-number-generators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-number-generators : Updates an existing voucherNumberGenerator.
     *
     * @param voucherNumberGenerator the voucherNumberGenerator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherNumberGenerator,
     * or with status 400 (Bad Request) if the voucherNumberGenerator is not valid,
     * or with status 500 (Internal Server Error) if the voucherNumberGenerator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-number-generators")
    public ResponseEntity<VoucherNumberGenerator> updateVoucherNumberGenerator(@RequestBody VoucherNumberGenerator voucherNumberGenerator) throws URISyntaxException {
        log.debug("REST request to update VoucherNumberGenerator : {}", voucherNumberGenerator);
        if (voucherNumberGenerator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VoucherNumberGenerator result = voucherNumberGeneratorRepository.save(voucherNumberGenerator);
        voucherNumberGeneratorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherNumberGenerator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-number-generators : get all the voucherNumberGenerators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of voucherNumberGenerators in body
     */
    @GetMapping("/voucher-number-generators")
    public List<VoucherNumberGenerator> getAllVoucherNumberGenerators() {
        log.debug("REST request to get all VoucherNumberGenerators");
        return voucherNumberGeneratorRepository.findAll();
    }

    /**
     * GET  /voucher-number-generators/:id : get the "id" voucherNumberGenerator.
     *
     * @param id the id of the voucherNumberGenerator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherNumberGenerator, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-number-generators/{id}")
    public ResponseEntity<VoucherNumberGenerator> getVoucherNumberGenerator(@PathVariable Long id) {
        log.debug("REST request to get VoucherNumberGenerator : {}", id);
        Optional<VoucherNumberGenerator> voucherNumberGenerator = voucherNumberGeneratorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voucherNumberGenerator);
    }

    /**
     * DELETE  /voucher-number-generators/:id : delete the "id" voucherNumberGenerator.
     *
     * @param id the id of the voucherNumberGenerator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-number-generators/{id}")
    public ResponseEntity<Void> deleteVoucherNumberGenerator(@PathVariable Long id) {
        log.debug("REST request to delete VoucherNumberGenerator : {}", id);
        voucherNumberGeneratorRepository.deleteById(id);
        voucherNumberGeneratorSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/voucher-number-generators?query=:query : search for the voucherNumberGenerator corresponding
     * to the query.
     *
     * @param query the query of the voucherNumberGenerator search
     * @return the result of the search
     */
    @GetMapping("/_search/voucher-number-generators")
    public List<VoucherNumberGenerator> searchVoucherNumberGenerators(@RequestParam String query) {
        log.debug("REST request to search VoucherNumberGenerators for query {}", query);
        return StreamSupport
            .stream(voucherNumberGeneratorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
