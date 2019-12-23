package org.soptorshi.web.rest;
import org.soptorshi.domain.MontlySalaryVouchers;
import org.soptorshi.repository.MontlySalaryVouchersRepository;
import org.soptorshi.repository.search.MontlySalaryVouchersSearchRepository;
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
 * REST controller for managing MontlySalaryVouchers.
 */
@RestController
@RequestMapping("/api")
public class MontlySalaryVouchersResource {

    private final Logger log = LoggerFactory.getLogger(MontlySalaryVouchersResource.class);

    private static final String ENTITY_NAME = "montlySalaryVouchers";

    private final MontlySalaryVouchersRepository montlySalaryVouchersRepository;

    private final MontlySalaryVouchersSearchRepository montlySalaryVouchersSearchRepository;

    public MontlySalaryVouchersResource(MontlySalaryVouchersRepository montlySalaryVouchersRepository, MontlySalaryVouchersSearchRepository montlySalaryVouchersSearchRepository) {
        this.montlySalaryVouchersRepository = montlySalaryVouchersRepository;
        this.montlySalaryVouchersSearchRepository = montlySalaryVouchersSearchRepository;
    }

    /**
     * POST  /montly-salary-vouchers : Create a new montlySalaryVouchers.
     *
     * @param montlySalaryVouchers the montlySalaryVouchers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new montlySalaryVouchers, or with status 400 (Bad Request) if the montlySalaryVouchers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/montly-salary-vouchers")
    public ResponseEntity<MontlySalaryVouchers> createMontlySalaryVouchers(@RequestBody MontlySalaryVouchers montlySalaryVouchers) throws URISyntaxException {
        log.debug("REST request to save MontlySalaryVouchers : {}", montlySalaryVouchers);
        if (montlySalaryVouchers.getId() != null) {
            throw new BadRequestAlertException("A new montlySalaryVouchers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MontlySalaryVouchers result = montlySalaryVouchersRepository.save(montlySalaryVouchers);
        montlySalaryVouchersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/montly-salary-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /montly-salary-vouchers : Updates an existing montlySalaryVouchers.
     *
     * @param montlySalaryVouchers the montlySalaryVouchers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated montlySalaryVouchers,
     * or with status 400 (Bad Request) if the montlySalaryVouchers is not valid,
     * or with status 500 (Internal Server Error) if the montlySalaryVouchers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/montly-salary-vouchers")
    public ResponseEntity<MontlySalaryVouchers> updateMontlySalaryVouchers(@RequestBody MontlySalaryVouchers montlySalaryVouchers) throws URISyntaxException {
        log.debug("REST request to update MontlySalaryVouchers : {}", montlySalaryVouchers);
        if (montlySalaryVouchers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MontlySalaryVouchers result = montlySalaryVouchersRepository.save(montlySalaryVouchers);
        montlySalaryVouchersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, montlySalaryVouchers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /montly-salary-vouchers : get all the montlySalaryVouchers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of montlySalaryVouchers in body
     */
    @GetMapping("/montly-salary-vouchers")
    public List<MontlySalaryVouchers> getAllMontlySalaryVouchers() {
        log.debug("REST request to get all MontlySalaryVouchers");
        return montlySalaryVouchersRepository.findAll();
    }

    /**
     * GET  /montly-salary-vouchers/:id : get the "id" montlySalaryVouchers.
     *
     * @param id the id of the montlySalaryVouchers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the montlySalaryVouchers, or with status 404 (Not Found)
     */
    @GetMapping("/montly-salary-vouchers/{id}")
    public ResponseEntity<MontlySalaryVouchers> getMontlySalaryVouchers(@PathVariable Long id) {
        log.debug("REST request to get MontlySalaryVouchers : {}", id);
        Optional<MontlySalaryVouchers> montlySalaryVouchers = montlySalaryVouchersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(montlySalaryVouchers);
    }

    /**
     * DELETE  /montly-salary-vouchers/:id : delete the "id" montlySalaryVouchers.
     *
     * @param id the id of the montlySalaryVouchers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/montly-salary-vouchers/{id}")
    public ResponseEntity<Void> deleteMontlySalaryVouchers(@PathVariable Long id) {
        log.debug("REST request to delete MontlySalaryVouchers : {}", id);
        montlySalaryVouchersRepository.deleteById(id);
        montlySalaryVouchersSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/montly-salary-vouchers?query=:query : search for the montlySalaryVouchers corresponding
     * to the query.
     *
     * @param query the query of the montlySalaryVouchers search
     * @return the result of the search
     */
    @GetMapping("/_search/montly-salary-vouchers")
    public List<MontlySalaryVouchers> searchMontlySalaryVouchers(@RequestParam String query) {
        log.debug("REST request to search MontlySalaryVouchers for query {}", query);
        return StreamSupport
            .stream(montlySalaryVouchersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
