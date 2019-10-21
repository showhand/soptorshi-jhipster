package org.soptorshi.web.rest;
import org.soptorshi.domain.PaymentVoucherGenerator;
import org.soptorshi.repository.PaymentVoucherGeneratorRepository;
import org.soptorshi.repository.search.PaymentVoucherGeneratorSearchRepository;
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
 * REST controller for managing PaymentVoucherGenerator.
 */
@RestController
@RequestMapping("/api")
public class PaymentVoucherGeneratorResource {

    private final Logger log = LoggerFactory.getLogger(PaymentVoucherGeneratorResource.class);

    private static final String ENTITY_NAME = "paymentVoucherGenerator";

    private final PaymentVoucherGeneratorRepository paymentVoucherGeneratorRepository;

    private final PaymentVoucherGeneratorSearchRepository paymentVoucherGeneratorSearchRepository;

    public PaymentVoucherGeneratorResource(PaymentVoucherGeneratorRepository paymentVoucherGeneratorRepository, PaymentVoucherGeneratorSearchRepository paymentVoucherGeneratorSearchRepository) {
        this.paymentVoucherGeneratorRepository = paymentVoucherGeneratorRepository;
        this.paymentVoucherGeneratorSearchRepository = paymentVoucherGeneratorSearchRepository;
    }

    /**
     * POST  /payment-voucher-generators : Create a new paymentVoucherGenerator.
     *
     * @param paymentVoucherGenerator the paymentVoucherGenerator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentVoucherGenerator, or with status 400 (Bad Request) if the paymentVoucherGenerator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-voucher-generators")
    public ResponseEntity<PaymentVoucherGenerator> createPaymentVoucherGenerator(@RequestBody PaymentVoucherGenerator paymentVoucherGenerator) throws URISyntaxException {
        log.debug("REST request to save PaymentVoucherGenerator : {}", paymentVoucherGenerator);
        if (paymentVoucherGenerator.getId() != null) {
            throw new BadRequestAlertException("A new paymentVoucherGenerator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentVoucherGenerator result = paymentVoucherGeneratorRepository.save(paymentVoucherGenerator);
        paymentVoucherGeneratorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/payment-voucher-generators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-voucher-generators : Updates an existing paymentVoucherGenerator.
     *
     * @param paymentVoucherGenerator the paymentVoucherGenerator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentVoucherGenerator,
     * or with status 400 (Bad Request) if the paymentVoucherGenerator is not valid,
     * or with status 500 (Internal Server Error) if the paymentVoucherGenerator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-voucher-generators")
    public ResponseEntity<PaymentVoucherGenerator> updatePaymentVoucherGenerator(@RequestBody PaymentVoucherGenerator paymentVoucherGenerator) throws URISyntaxException {
        log.debug("REST request to update PaymentVoucherGenerator : {}", paymentVoucherGenerator);
        if (paymentVoucherGenerator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentVoucherGenerator result = paymentVoucherGeneratorRepository.save(paymentVoucherGenerator);
        paymentVoucherGeneratorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentVoucherGenerator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-voucher-generators : get all the paymentVoucherGenerators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentVoucherGenerators in body
     */
    @GetMapping("/payment-voucher-generators")
    public List<PaymentVoucherGenerator> getAllPaymentVoucherGenerators() {
        log.debug("REST request to get all PaymentVoucherGenerators");
        return paymentVoucherGeneratorRepository.findAll();
    }

    /**
     * GET  /payment-voucher-generators/:id : get the "id" paymentVoucherGenerator.
     *
     * @param id the id of the paymentVoucherGenerator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentVoucherGenerator, or with status 404 (Not Found)
     */
    @GetMapping("/payment-voucher-generators/{id}")
    public ResponseEntity<PaymentVoucherGenerator> getPaymentVoucherGenerator(@PathVariable Long id) {
        log.debug("REST request to get PaymentVoucherGenerator : {}", id);
        Optional<PaymentVoucherGenerator> paymentVoucherGenerator = paymentVoucherGeneratorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentVoucherGenerator);
    }

    /**
     * DELETE  /payment-voucher-generators/:id : delete the "id" paymentVoucherGenerator.
     *
     * @param id the id of the paymentVoucherGenerator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-voucher-generators/{id}")
    public ResponseEntity<Void> deletePaymentVoucherGenerator(@PathVariable Long id) {
        log.debug("REST request to delete PaymentVoucherGenerator : {}", id);
        paymentVoucherGeneratorRepository.deleteById(id);
        paymentVoucherGeneratorSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/payment-voucher-generators?query=:query : search for the paymentVoucherGenerator corresponding
     * to the query.
     *
     * @param query the query of the paymentVoucherGenerator search
     * @return the result of the search
     */
    @GetMapping("/_search/payment-voucher-generators")
    public List<PaymentVoucherGenerator> searchPaymentVoucherGenerators(@RequestParam String query) {
        log.debug("REST request to search PaymentVoucherGenerators for query {}", query);
        return StreamSupport
            .stream(paymentVoucherGeneratorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
