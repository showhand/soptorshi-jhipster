package org.soptorshi.service;

import org.soptorshi.domain.AccountBalance;
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.search.AccountBalanceSearchRepository;
import org.soptorshi.service.dto.AccountBalanceDTO;
import org.soptorshi.service.mapper.AccountBalanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AccountBalance.
 */
@Service
@Transactional
public class AccountBalanceService {

    private final Logger log = LoggerFactory.getLogger(AccountBalanceService.class);

    private final AccountBalanceRepository accountBalanceRepository;

    private final AccountBalanceMapper accountBalanceMapper;

    private final AccountBalanceSearchRepository accountBalanceSearchRepository;

    public AccountBalanceService(AccountBalanceRepository accountBalanceRepository, AccountBalanceMapper accountBalanceMapper, AccountBalanceSearchRepository accountBalanceSearchRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.accountBalanceMapper = accountBalanceMapper;
        this.accountBalanceSearchRepository = accountBalanceSearchRepository;
    }

    /**
     * Save a accountBalance.
     *
     * @param accountBalanceDTO the entity to save
     * @return the persisted entity
     */
    public AccountBalanceDTO save(AccountBalanceDTO accountBalanceDTO) {
        log.debug("Request to save AccountBalance : {}", accountBalanceDTO);
        AccountBalance accountBalance = accountBalanceMapper.toEntity(accountBalanceDTO);
        accountBalance = accountBalanceRepository.save(accountBalance);
        AccountBalanceDTO result = accountBalanceMapper.toDto(accountBalance);
        accountBalanceSearchRepository.save(accountBalance);
        return result;
    }

    /**
     * Get all the accountBalances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AccountBalanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountBalances");
        return accountBalanceRepository.findAll(pageable)
            .map(accountBalanceMapper::toDto);
    }


    /**
     * Get one accountBalance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AccountBalanceDTO> findOne(Long id) {
        log.debug("Request to get AccountBalance : {}", id);
        return accountBalanceRepository.findById(id)
            .map(accountBalanceMapper::toDto);
    }

    /**
     * Delete the accountBalance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountBalance : {}", id);
        accountBalanceRepository.deleteById(id);
        accountBalanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the accountBalance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AccountBalanceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AccountBalances for query {}", query);
        return accountBalanceSearchRepository.search(queryStringQuery(query), pageable)
            .map(accountBalanceMapper::toDto);
    }
}
