package org.soptorshi.web.rest;
import org.soptorshi.service.AccountBalanceService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.AccountBalanceDTO;
import org.soptorshi.service.dto.AccountBalanceCriteria;
import org.soptorshi.service.AccountBalanceQueryService;
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
 * REST controller for managing AccountBalance.
 */
@RestController
@RequestMapping("/api")
public class AccountBalanceResource {

    private final Logger log = LoggerFactory.getLogger(AccountBalanceResource.class);

    private static final String ENTITY_NAME = "accountBalance";

    private final AccountBalanceService accountBalanceService;

    private final AccountBalanceQueryService accountBalanceQueryService;

    public AccountBalanceResource(AccountBalanceService accountBalanceService, AccountBalanceQueryService accountBalanceQueryService) {
        this.accountBalanceService = accountBalanceService;
        this.accountBalanceQueryService = accountBalanceQueryService;
    }

    /**
     * POST  /account-balances : Create a new accountBalance.
     *
     * @param accountBalanceDTO the accountBalanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountBalanceDTO, or with status 400 (Bad Request) if the accountBalance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-balances")
    public ResponseEntity<AccountBalanceDTO> createAccountBalance(@RequestBody AccountBalanceDTO accountBalanceDTO) throws URISyntaxException {
        log.debug("REST request to save AccountBalance : {}", accountBalanceDTO);
        if (accountBalanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountBalance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountBalanceDTO result = accountBalanceService.save(accountBalanceDTO);
        return ResponseEntity.created(new URI("/api/account-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-balances : Updates an existing accountBalance.
     *
     * @param accountBalanceDTO the accountBalanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountBalanceDTO,
     * or with status 400 (Bad Request) if the accountBalanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the accountBalanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-balances")
    public ResponseEntity<AccountBalanceDTO> updateAccountBalance(@RequestBody AccountBalanceDTO accountBalanceDTO) throws URISyntaxException {
        log.debug("REST request to update AccountBalance : {}", accountBalanceDTO);
        if (accountBalanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountBalanceDTO result = accountBalanceService.save(accountBalanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountBalanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-balances : get all the accountBalances.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of accountBalances in body
     */
    @GetMapping("/account-balances")
    public ResponseEntity<List<AccountBalanceDTO>> getAllAccountBalances(AccountBalanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AccountBalances by criteria: {}", criteria);
        Page<AccountBalanceDTO> page = accountBalanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account-balances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /account-balances/count : count all the accountBalances.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/account-balances/count")
    public ResponseEntity<Long> countAccountBalances(AccountBalanceCriteria criteria) {
        log.debug("REST request to count AccountBalances by criteria: {}", criteria);
        return ResponseEntity.ok().body(accountBalanceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /account-balances/:id : get the "id" accountBalance.
     *
     * @param id the id of the accountBalanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountBalanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/account-balances/{id}")
    public ResponseEntity<AccountBalanceDTO> getAccountBalance(@PathVariable Long id) {
        log.debug("REST request to get AccountBalance : {}", id);
        Optional<AccountBalanceDTO> accountBalanceDTO = accountBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountBalanceDTO);
    }

    /**
     * DELETE  /account-balances/:id : delete the "id" accountBalance.
     *
     * @param id the id of the accountBalanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-balances/{id}")
    public ResponseEntity<Void> deleteAccountBalance(@PathVariable Long id) {
        log.debug("REST request to delete AccountBalance : {}", id);
        accountBalanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/account-balances?query=:query : search for the accountBalance corresponding
     * to the query.
     *
     * @param query the query of the accountBalance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/account-balances")
    public ResponseEntity<List<AccountBalanceDTO>> searchAccountBalances(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AccountBalances for query {}", query);
        Page<AccountBalanceDTO> page = accountBalanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/account-balances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
