package org.soptorshi.service;

import org.soptorshi.domain.MonthlyBalance;
import org.soptorshi.repository.MonthlyBalanceRepository;
import org.soptorshi.repository.search.MonthlyBalanceSearchRepository;
import org.soptorshi.service.dto.MonthlyBalanceDTO;
import org.soptorshi.service.mapper.MonthlyBalanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MonthlyBalance.
 */
@Service
@Transactional
public class MonthlyBalanceService {

    private final Logger log = LoggerFactory.getLogger(MonthlyBalanceService.class);

    private final MonthlyBalanceRepository monthlyBalanceRepository;

    private final MonthlyBalanceMapper monthlyBalanceMapper;

    private final MonthlyBalanceSearchRepository monthlyBalanceSearchRepository;

    public MonthlyBalanceService(MonthlyBalanceRepository monthlyBalanceRepository, MonthlyBalanceMapper monthlyBalanceMapper, MonthlyBalanceSearchRepository monthlyBalanceSearchRepository) {
        this.monthlyBalanceRepository = monthlyBalanceRepository;
        this.monthlyBalanceMapper = monthlyBalanceMapper;
        this.monthlyBalanceSearchRepository = monthlyBalanceSearchRepository;
    }

    /**
     * Save a monthlyBalance.
     *
     * @param monthlyBalanceDTO the entity to save
     * @return the persisted entity
     */
    public MonthlyBalanceDTO save(MonthlyBalanceDTO monthlyBalanceDTO) {
        log.debug("Request to save MonthlyBalance : {}", monthlyBalanceDTO);
        MonthlyBalance monthlyBalance = monthlyBalanceMapper.toEntity(monthlyBalanceDTO);
        monthlyBalance = monthlyBalanceRepository.save(monthlyBalance);
        MonthlyBalanceDTO result = monthlyBalanceMapper.toDto(monthlyBalance);
        monthlyBalanceSearchRepository.save(monthlyBalance);
        return result;
    }

    /**
     * Get all the monthlyBalances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MonthlyBalanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MonthlyBalances");
        return monthlyBalanceRepository.findAll(pageable)
            .map(monthlyBalanceMapper::toDto);
    }


    /**
     * Get one monthlyBalance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MonthlyBalanceDTO> findOne(Long id) {
        log.debug("Request to get MonthlyBalance : {}", id);
        return monthlyBalanceRepository.findById(id)
            .map(monthlyBalanceMapper::toDto);
    }

    /**
     * Delete the monthlyBalance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlyBalance : {}", id);
        monthlyBalanceRepository.deleteById(id);
        monthlyBalanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the monthlyBalance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MonthlyBalanceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MonthlyBalances for query {}", query);
        return monthlyBalanceSearchRepository.search(queryStringQuery(query), pageable)
            .map(monthlyBalanceMapper::toDto);
    }
}
