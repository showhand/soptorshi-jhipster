package org.soptorshi.service;

import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.repository.search.MonthlySalarySearchRepository;
import org.soptorshi.service.dto.MonthlySalaryDTO;
import org.soptorshi.service.mapper.MonthlySalaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MonthlySalary.
 */
@Service
@Transactional
public class MonthlySalaryService {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryService.class);

    private final MonthlySalaryRepository monthlySalaryRepository;

    private final MonthlySalaryMapper monthlySalaryMapper;

    private final MonthlySalarySearchRepository monthlySalarySearchRepository;

    public MonthlySalaryService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryMapper monthlySalaryMapper, MonthlySalarySearchRepository monthlySalarySearchRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryMapper = monthlySalaryMapper;
        this.monthlySalarySearchRepository = monthlySalarySearchRepository;
    }

    /**
     * Save a monthlySalary.
     *
     * @param monthlySalaryDTO the entity to save
     * @return the persisted entity
     */
    public MonthlySalaryDTO save(MonthlySalaryDTO monthlySalaryDTO) {
        log.debug("Request to save MonthlySalary : {}", monthlySalaryDTO);
        MonthlySalary monthlySalary = monthlySalaryMapper.toEntity(monthlySalaryDTO);
        monthlySalary = monthlySalaryRepository.save(monthlySalary);
        MonthlySalaryDTO result = monthlySalaryMapper.toDto(monthlySalary);
        monthlySalarySearchRepository.save(monthlySalary);
        return result;
    }

    /**
     * Get all the monthlySalaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MonthlySalaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MonthlySalaries");
        return monthlySalaryRepository.findAll(pageable)
            .map(monthlySalaryMapper::toDto);
    }


    /**
     * Get one monthlySalary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MonthlySalaryDTO> findOne(Long id) {
        log.debug("Request to get MonthlySalary : {}", id);
        return monthlySalaryRepository.findById(id)
            .map(monthlySalaryMapper::toDto);
    }

    /**
     * Delete the monthlySalary by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlySalary : {}", id);
        monthlySalaryRepository.deleteById(id);
        monthlySalarySearchRepository.deleteById(id);
    }

    /**
     * Search for the monthlySalary corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MonthlySalaryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MonthlySalaries for query {}", query);
        return monthlySalarySearchRepository.search(queryStringQuery(query), pageable)
            .map(monthlySalaryMapper::toDto);
    }
}
