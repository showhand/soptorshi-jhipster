package org.soptorshi.service;

import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.repository.FinancialAccountYearRepository;
import org.soptorshi.repository.search.FinancialAccountYearSearchRepository;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.mapper.FinancialAccountYearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FinancialAccountYear.
 */
@Service
@Transactional
public class FinancialAccountYearService {

    private final Logger log = LoggerFactory.getLogger(FinancialAccountYearService.class);

    private final FinancialAccountYearRepository financialAccountYearRepository;

    private final FinancialAccountYearMapper financialAccountYearMapper;

    private final FinancialAccountYearSearchRepository financialAccountYearSearchRepository;

    public FinancialAccountYearService(FinancialAccountYearRepository financialAccountYearRepository, FinancialAccountYearMapper financialAccountYearMapper, FinancialAccountYearSearchRepository financialAccountYearSearchRepository) {
        this.financialAccountYearRepository = financialAccountYearRepository;
        this.financialAccountYearMapper = financialAccountYearMapper;
        this.financialAccountYearSearchRepository = financialAccountYearSearchRepository;
    }

    /**
     * Save a financialAccountYear.
     *
     * @param financialAccountYearDTO the entity to save
     * @return the persisted entity
     */
    public FinancialAccountYearDTO save(FinancialAccountYearDTO financialAccountYearDTO) {
        log.debug("Request to save FinancialAccountYear : {}", financialAccountYearDTO);
        FinancialAccountYear financialAccountYear = financialAccountYearMapper.toEntity(financialAccountYearDTO);
        financialAccountYear = financialAccountYearRepository.save(financialAccountYear);
        FinancialAccountYearDTO result = financialAccountYearMapper.toDto(financialAccountYear);
        financialAccountYearSearchRepository.save(financialAccountYear);
        return result;
    }

    /**
     * Get all the financialAccountYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FinancialAccountYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinancialAccountYears");
        return financialAccountYearRepository.findAll(pageable)
            .map(financialAccountYearMapper::toDto);
    }


    /**
     * Get one financialAccountYear by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FinancialAccountYearDTO> findOne(Long id) {
        log.debug("Request to get FinancialAccountYear : {}", id);
        return financialAccountYearRepository.findById(id)
            .map(financialAccountYearMapper::toDto);
    }

    /**
     * Delete the financialAccountYear by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FinancialAccountYear : {}", id);
        financialAccountYearRepository.deleteById(id);
        financialAccountYearSearchRepository.deleteById(id);
    }

    /**
     * Search for the financialAccountYear corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FinancialAccountYearDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FinancialAccountYears for query {}", query);
        return financialAccountYearSearchRepository.search(queryStringQuery(query), pageable)
            .map(financialAccountYearMapper::toDto);
    }
}
