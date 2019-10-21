package org.soptorshi.web.rest;
import org.soptorshi.domain.JournalVoucherGenerator;
import org.soptorshi.repository.JournalVoucherGeneratorRepository;
import org.soptorshi.repository.search.JournalVoucherGeneratorSearchRepository;
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
 * REST controller for managing JournalVoucherGenerator.
 */
@RestController
@RequestMapping("/api")
public class JournalVoucherGeneratorResource {

    private final Logger log = LoggerFactory.getLogger(JournalVoucherGeneratorResource.class);

    private static final String ENTITY_NAME = "journalVoucherGenerator";

    private final JournalVoucherGeneratorRepository journalVoucherGeneratorRepository;

    private final JournalVoucherGeneratorSearchRepository journalVoucherGeneratorSearchRepository;

    public JournalVoucherGeneratorResource(JournalVoucherGeneratorRepository journalVoucherGeneratorRepository, JournalVoucherGeneratorSearchRepository journalVoucherGeneratorSearchRepository) {
        this.journalVoucherGeneratorRepository = journalVoucherGeneratorRepository;
        this.journalVoucherGeneratorSearchRepository = journalVoucherGeneratorSearchRepository;
    }

    /**
     * POST  /journal-voucher-generators : Create a new journalVoucherGenerator.
     *
     * @param journalVoucherGenerator the journalVoucherGenerator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journalVoucherGenerator, or with status 400 (Bad Request) if the journalVoucherGenerator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journal-voucher-generators")
    public ResponseEntity<JournalVoucherGenerator> createJournalVoucherGenerator(@RequestBody JournalVoucherGenerator journalVoucherGenerator) throws URISyntaxException {
        log.debug("REST request to save JournalVoucherGenerator : {}", journalVoucherGenerator);
        if (journalVoucherGenerator.getId() != null) {
            throw new BadRequestAlertException("A new journalVoucherGenerator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JournalVoucherGenerator result = journalVoucherGeneratorRepository.save(journalVoucherGenerator);
        journalVoucherGeneratorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/journal-voucher-generators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journal-voucher-generators : Updates an existing journalVoucherGenerator.
     *
     * @param journalVoucherGenerator the journalVoucherGenerator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journalVoucherGenerator,
     * or with status 400 (Bad Request) if the journalVoucherGenerator is not valid,
     * or with status 500 (Internal Server Error) if the journalVoucherGenerator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journal-voucher-generators")
    public ResponseEntity<JournalVoucherGenerator> updateJournalVoucherGenerator(@RequestBody JournalVoucherGenerator journalVoucherGenerator) throws URISyntaxException {
        log.debug("REST request to update JournalVoucherGenerator : {}", journalVoucherGenerator);
        if (journalVoucherGenerator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JournalVoucherGenerator result = journalVoucherGeneratorRepository.save(journalVoucherGenerator);
        journalVoucherGeneratorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journalVoucherGenerator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journal-voucher-generators : get all the journalVoucherGenerators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of journalVoucherGenerators in body
     */
    @GetMapping("/journal-voucher-generators")
    public List<JournalVoucherGenerator> getAllJournalVoucherGenerators() {
        log.debug("REST request to get all JournalVoucherGenerators");
        return journalVoucherGeneratorRepository.findAll();
    }

    /**
     * GET  /journal-voucher-generators/:id : get the "id" journalVoucherGenerator.
     *
     * @param id the id of the journalVoucherGenerator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalVoucherGenerator, or with status 404 (Not Found)
     */
    @GetMapping("/journal-voucher-generators/{id}")
    public ResponseEntity<JournalVoucherGenerator> getJournalVoucherGenerator(@PathVariable Long id) {
        log.debug("REST request to get JournalVoucherGenerator : {}", id);
        Optional<JournalVoucherGenerator> journalVoucherGenerator = journalVoucherGeneratorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(journalVoucherGenerator);
    }

    /**
     * DELETE  /journal-voucher-generators/:id : delete the "id" journalVoucherGenerator.
     *
     * @param id the id of the journalVoucherGenerator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journal-voucher-generators/{id}")
    public ResponseEntity<Void> deleteJournalVoucherGenerator(@PathVariable Long id) {
        log.debug("REST request to delete JournalVoucherGenerator : {}", id);
        journalVoucherGeneratorRepository.deleteById(id);
        journalVoucherGeneratorSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/journal-voucher-generators?query=:query : search for the journalVoucherGenerator corresponding
     * to the query.
     *
     * @param query the query of the journalVoucherGenerator search
     * @return the result of the search
     */
    @GetMapping("/_search/journal-voucher-generators")
    public List<JournalVoucherGenerator> searchJournalVoucherGenerators(@RequestParam String query) {
        log.debug("REST request to search JournalVoucherGenerators for query {}", query);
        return StreamSupport
            .stream(journalVoucherGeneratorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
