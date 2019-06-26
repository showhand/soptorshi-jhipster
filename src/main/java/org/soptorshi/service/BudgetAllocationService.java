package org.soptorshi.service;

import org.soptorshi.domain.BudgetAllocation;
import org.soptorshi.repository.BudgetAllocationRepository;
import org.soptorshi.repository.search.BudgetAllocationSearchRepository;
import org.soptorshi.service.dto.BudgetAllocationDTO;
import org.soptorshi.service.mapper.BudgetAllocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BudgetAllocation.
 */
@Service
@Transactional
public class BudgetAllocationService {

    private final Logger log = LoggerFactory.getLogger(BudgetAllocationService.class);

    private final BudgetAllocationRepository budgetAllocationRepository;

    private final BudgetAllocationMapper budgetAllocationMapper;

    private final BudgetAllocationSearchRepository budgetAllocationSearchRepository;

    public BudgetAllocationService(BudgetAllocationRepository budgetAllocationRepository, BudgetAllocationMapper budgetAllocationMapper, BudgetAllocationSearchRepository budgetAllocationSearchRepository) {
        this.budgetAllocationRepository = budgetAllocationRepository;
        this.budgetAllocationMapper = budgetAllocationMapper;
        this.budgetAllocationSearchRepository = budgetAllocationSearchRepository;
    }

    /**
     * Save a budgetAllocation.
     *
     * @param budgetAllocationDTO the entity to save
     * @return the persisted entity
     */
    public BudgetAllocationDTO save(BudgetAllocationDTO budgetAllocationDTO) {
        log.debug("Request to save BudgetAllocation : {}", budgetAllocationDTO);
        BudgetAllocation budgetAllocation = budgetAllocationMapper.toEntity(budgetAllocationDTO);
        budgetAllocation = budgetAllocationRepository.save(budgetAllocation);
        BudgetAllocationDTO result = budgetAllocationMapper.toDto(budgetAllocation);
        budgetAllocationSearchRepository.save(budgetAllocation);
        return result;
    }

    /**
     * Get all the budgetAllocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BudgetAllocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BudgetAllocations");
        return budgetAllocationRepository.findAll(pageable)
            .map(budgetAllocationMapper::toDto);
    }


    /**
     * Get one budgetAllocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BudgetAllocationDTO> findOne(Long id) {
        log.debug("Request to get BudgetAllocation : {}", id);
        return budgetAllocationRepository.findById(id)
            .map(budgetAllocationMapper::toDto);
    }

    /**
     * Delete the budgetAllocation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BudgetAllocation : {}", id);
        budgetAllocationRepository.deleteById(id);
        budgetAllocationSearchRepository.deleteById(id);
    }

    /**
     * Search for the budgetAllocation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BudgetAllocationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BudgetAllocations for query {}", query);
        return budgetAllocationSearchRepository.search(queryStringQuery(query), pageable)
            .map(budgetAllocationMapper::toDto);
    }
}
