package org.soptorshi.web.rest;
import org.soptorshi.domain.ReceiptVoucherGenerator;
import org.soptorshi.repository.ReceiptVoucherGeneratorRepository;
import org.soptorshi.repository.search.ReceiptVoucherGeneratorSearchRepository;
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
 * REST controller for managing ReceiptVoucherGenerator.
 */
@RestController
@RequestMapping("/api")
public class ReceiptVoucherGeneratorResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptVoucherGeneratorResource.class);

    private static final String ENTITY_NAME = "receiptVoucherGenerator";

    private final ReceiptVoucherGeneratorRepository receiptVoucherGeneratorRepository;

    private final ReceiptVoucherGeneratorSearchRepository receiptVoucherGeneratorSearchRepository;

    public ReceiptVoucherGeneratorResource(ReceiptVoucherGeneratorRepository receiptVoucherGeneratorRepository, ReceiptVoucherGeneratorSearchRepository receiptVoucherGeneratorSearchRepository) {
        this.receiptVoucherGeneratorRepository = receiptVoucherGeneratorRepository;
        this.receiptVoucherGeneratorSearchRepository = receiptVoucherGeneratorSearchRepository;
    }

    /**
     * POST  /receipt-voucher-generators : Create a new receiptVoucherGenerator.
     *
     * @param receiptVoucherGenerator the receiptVoucherGenerator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receiptVoucherGenerator, or with status 400 (Bad Request) if the receiptVoucherGenerator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/receipt-voucher-generators")
    public ResponseEntity<ReceiptVoucherGenerator> createReceiptVoucherGenerator(@RequestBody ReceiptVoucherGenerator receiptVoucherGenerator) throws URISyntaxException {
        log.debug("REST request to save ReceiptVoucherGenerator : {}", receiptVoucherGenerator);
        if (receiptVoucherGenerator.getId() != null) {
            throw new BadRequestAlertException("A new receiptVoucherGenerator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceiptVoucherGenerator result = receiptVoucherGeneratorRepository.save(receiptVoucherGenerator);
        receiptVoucherGeneratorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/receipt-voucher-generators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /receipt-voucher-generators : Updates an existing receiptVoucherGenerator.
     *
     * @param receiptVoucherGenerator the receiptVoucherGenerator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receiptVoucherGenerator,
     * or with status 400 (Bad Request) if the receiptVoucherGenerator is not valid,
     * or with status 500 (Internal Server Error) if the receiptVoucherGenerator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/receipt-voucher-generators")
    public ResponseEntity<ReceiptVoucherGenerator> updateReceiptVoucherGenerator(@RequestBody ReceiptVoucherGenerator receiptVoucherGenerator) throws URISyntaxException {
        log.debug("REST request to update ReceiptVoucherGenerator : {}", receiptVoucherGenerator);
        if (receiptVoucherGenerator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReceiptVoucherGenerator result = receiptVoucherGeneratorRepository.save(receiptVoucherGenerator);
        receiptVoucherGeneratorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, receiptVoucherGenerator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /receipt-voucher-generators : get all the receiptVoucherGenerators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of receiptVoucherGenerators in body
     */
    @GetMapping("/receipt-voucher-generators")
    public List<ReceiptVoucherGenerator> getAllReceiptVoucherGenerators() {
        log.debug("REST request to get all ReceiptVoucherGenerators");
        return receiptVoucherGeneratorRepository.findAll();
    }

    /**
     * GET  /receipt-voucher-generators/:id : get the "id" receiptVoucherGenerator.
     *
     * @param id the id of the receiptVoucherGenerator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receiptVoucherGenerator, or with status 404 (Not Found)
     */
    @GetMapping("/receipt-voucher-generators/{id}")
    public ResponseEntity<ReceiptVoucherGenerator> getReceiptVoucherGenerator(@PathVariable Long id) {
        log.debug("REST request to get ReceiptVoucherGenerator : {}", id);
        Optional<ReceiptVoucherGenerator> receiptVoucherGenerator = receiptVoucherGeneratorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(receiptVoucherGenerator);
    }

    /**
     * DELETE  /receipt-voucher-generators/:id : delete the "id" receiptVoucherGenerator.
     *
     * @param id the id of the receiptVoucherGenerator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/receipt-voucher-generators/{id}")
    public ResponseEntity<Void> deleteReceiptVoucherGenerator(@PathVariable Long id) {
        log.debug("REST request to delete ReceiptVoucherGenerator : {}", id);
        receiptVoucherGeneratorRepository.deleteById(id);
        receiptVoucherGeneratorSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/receipt-voucher-generators?query=:query : search for the receiptVoucherGenerator corresponding
     * to the query.
     *
     * @param query the query of the receiptVoucherGenerator search
     * @return the result of the search
     */
    @GetMapping("/_search/receipt-voucher-generators")
    public List<ReceiptVoucherGenerator> searchReceiptVoucherGenerators(@RequestParam String query) {
        log.debug("REST request to search ReceiptVoucherGenerators for query {}", query);
        return StreamSupport
            .stream(receiptVoucherGeneratorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
