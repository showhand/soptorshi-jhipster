package org.soptorshi.service;

import org.soptorshi.domain.DepreciationCalculation;
import org.soptorshi.repository.DepreciationCalculationRepository;
import org.soptorshi.repository.search.DepreciationCalculationSearchRepository;
import org.soptorshi.service.dto.DepreciationCalculationDTO;
import org.soptorshi.service.mapper.DepreciationCalculationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DepreciationCalculation.
 */
@Service
@Transactional
public class DepreciationCalculationService {

    private final Logger log = LoggerFactory.getLogger(DepreciationCalculationService.class);

    private final DepreciationCalculationRepository depreciationCalculationRepository;

    private final DepreciationCalculationMapper depreciationCalculationMapper;

    private final DepreciationCalculationSearchRepository depreciationCalculationSearchRepository;

    public DepreciationCalculationService(DepreciationCalculationRepository depreciationCalculationRepository, DepreciationCalculationMapper depreciationCalculationMapper, DepreciationCalculationSearchRepository depreciationCalculationSearchRepository) {
        this.depreciationCalculationRepository = depreciationCalculationRepository;
        this.depreciationCalculationMapper = depreciationCalculationMapper;
        this.depreciationCalculationSearchRepository = depreciationCalculationSearchRepository;
    }

    /**
     * Save a depreciationCalculation.
     *
     * @param depreciationCalculationDTO the entity to save
     * @return the persisted entity
     */
    public DepreciationCalculationDTO save(DepreciationCalculationDTO depreciationCalculationDTO) {
        log.debug("Request to save DepreciationCalculation : {}", depreciationCalculationDTO);
        DepreciationCalculation depreciationCalculation = depreciationCalculationMapper.toEntity(depreciationCalculationDTO);
        depreciationCalculation = depreciationCalculationRepository.save(depreciationCalculation);
        DepreciationCalculationDTO result = depreciationCalculationMapper.toDto(depreciationCalculation);
        depreciationCalculationSearchRepository.save(depreciationCalculation);
        return result;
    }

    /**
     * Get all the depreciationCalculations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepreciationCalculationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepreciationCalculations");
        return depreciationCalculationRepository.findAll(pageable)
            .map(depreciationCalculationMapper::toDto);
    }


    /**
     * Get one depreciationCalculation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DepreciationCalculationDTO> findOne(Long id) {
        log.debug("Request to get DepreciationCalculation : {}", id);
        return depreciationCalculationRepository.findById(id)
            .map(depreciationCalculationMapper::toDto);
    }

    /**
     * Delete the depreciationCalculation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DepreciationCalculation : {}", id);
        depreciationCalculationRepository.deleteById(id);
        depreciationCalculationSearchRepository.deleteById(id);
    }

    /**
     * Search for the depreciationCalculation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepreciationCalculationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DepreciationCalculations for query {}", query);
        return depreciationCalculationSearchRepository.search(queryStringQuery(query), pageable)
            .map(depreciationCalculationMapper::toDto);
    }
}
