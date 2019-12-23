package org.soptorshi.web.rest;
import org.soptorshi.domain.PayrollManagement;
import org.soptorshi.repository.PayrollManagementRepository;
import org.soptorshi.repository.search.PayrollManagementSearchRepository;
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
 * REST controller for managing PayrollManagement.
 */
@RestController
@RequestMapping("/api")
public class PayrollManagementResource {

    private final Logger log = LoggerFactory.getLogger(PayrollManagementResource.class);

    private static final String ENTITY_NAME = "payrollManagement";

    private final PayrollManagementRepository payrollManagementRepository;

    private final PayrollManagementSearchRepository payrollManagementSearchRepository;

    public PayrollManagementResource(PayrollManagementRepository payrollManagementRepository, PayrollManagementSearchRepository payrollManagementSearchRepository) {
        this.payrollManagementRepository = payrollManagementRepository;
        this.payrollManagementSearchRepository = payrollManagementSearchRepository;
    }

    /**
     * POST  /payroll-managements : Create a new payrollManagement.
     *
     * @param payrollManagement the payrollManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollManagement, or with status 400 (Bad Request) if the payrollManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-managements")
    public ResponseEntity<PayrollManagement> createPayrollManagement(@RequestBody PayrollManagement payrollManagement) throws URISyntaxException {
        log.debug("REST request to save PayrollManagement : {}", payrollManagement);
        if (payrollManagement.getId() != null) {
            throw new BadRequestAlertException("A new payrollManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollManagement result = payrollManagementRepository.save(payrollManagement);
        payrollManagementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/payroll-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-managements : Updates an existing payrollManagement.
     *
     * @param payrollManagement the payrollManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollManagement,
     * or with status 400 (Bad Request) if the payrollManagement is not valid,
     * or with status 500 (Internal Server Error) if the payrollManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-managements")
    public ResponseEntity<PayrollManagement> updatePayrollManagement(@RequestBody PayrollManagement payrollManagement) throws URISyntaxException {
        log.debug("REST request to update PayrollManagement : {}", payrollManagement);
        if (payrollManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PayrollManagement result = payrollManagementRepository.save(payrollManagement);
        payrollManagementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-managements : get all the payrollManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollManagements in body
     */
    @GetMapping("/payroll-managements")
    public List<PayrollManagement> getAllPayrollManagements() {
        log.debug("REST request to get all PayrollManagements");
        return payrollManagementRepository.findAll();
    }

    /**
     * GET  /payroll-managements/:id : get the "id" payrollManagement.
     *
     * @param id the id of the payrollManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollManagement, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-managements/{id}")
    public ResponseEntity<PayrollManagement> getPayrollManagement(@PathVariable Long id) {
        log.debug("REST request to get PayrollManagement : {}", id);
        Optional<PayrollManagement> payrollManagement = payrollManagementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payrollManagement);
    }

    /**
     * DELETE  /payroll-managements/:id : delete the "id" payrollManagement.
     *
     * @param id the id of the payrollManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-managements/{id}")
    public ResponseEntity<Void> deletePayrollManagement(@PathVariable Long id) {
        log.debug("REST request to delete PayrollManagement : {}", id);
        payrollManagementRepository.deleteById(id);
        payrollManagementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/payroll-managements?query=:query : search for the payrollManagement corresponding
     * to the query.
     *
     * @param query the query of the payrollManagement search
     * @return the result of the search
     */
    @GetMapping("/_search/payroll-managements")
    public List<PayrollManagement> searchPayrollManagements(@RequestParam String query) {
        log.debug("REST request to search PayrollManagements for query {}", query);
        return StreamSupport
            .stream(payrollManagementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
