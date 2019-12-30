package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.repository.CommercialBudgetRepository;
import org.soptorshi.repository.search.CommercialBudgetSearchRepository;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.mapper.CommercialBudgetMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialBudget.
 */
@Service
@Transactional
public class CommercialBudgetService {

    private final Logger log = LoggerFactory.getLogger(CommercialBudgetService.class);

    private final CommercialBudgetRepository commercialBudgetRepository;

    private final CommercialBudgetMapper commercialBudgetMapper;

    private final CommercialBudgetSearchRepository commercialBudgetSearchRepository;

    public CommercialBudgetService(CommercialBudgetRepository commercialBudgetRepository, CommercialBudgetMapper commercialBudgetMapper, CommercialBudgetSearchRepository commercialBudgetSearchRepository) {
        this.commercialBudgetRepository = commercialBudgetRepository;
        this.commercialBudgetMapper = commercialBudgetMapper;
        this.commercialBudgetSearchRepository = commercialBudgetSearchRepository;
    }

    /**
     * Save a commercialBudget.
     *
     * @param commercialBudgetDTO the entity to save
     * @return the persisted entity
     */
    public CommercialBudgetDTO save(CommercialBudgetDTO commercialBudgetDTO) {
        log.debug("Request to save CommercialBudget : {}", commercialBudgetDTO);
        CommercialBudget commercialBudget = commercialBudgetMapper.toEntity(commercialBudgetDTO);
        commercialBudget = commercialBudgetRepository.save(commercialBudget);
        CommercialBudgetDTO result = commercialBudgetMapper.toDto(commercialBudget);
        commercialBudgetSearchRepository.save(commercialBudget);
        return result;
    }

    /**
     * Get all the commercialBudgets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialBudgetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialBudgets");
        return commercialBudgetRepository.findAll(pageable)
            .map(commercialBudgetMapper::toDto);
    }


    /**
     * Get one commercialBudget by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialBudgetDTO> findOne(Long id) {
        log.debug("Request to get CommercialBudget : {}", id);
        return commercialBudgetRepository.findById(id)
            .map(commercialBudgetMapper::toDto);
    }

    /**
     * Delete the commercialBudget by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialBudget : {}", id);
        commercialBudgetRepository.deleteById(id);
        commercialBudgetSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialBudget corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialBudgetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialBudgets for query {}", query);
        return commercialBudgetSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialBudgetMapper::toDto);
    }
}
