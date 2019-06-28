package org.soptorshi.service;

import org.soptorshi.domain.TermsAndConditions;
import org.soptorshi.repository.TermsAndConditionsRepository;
import org.soptorshi.repository.search.TermsAndConditionsSearchRepository;
import org.soptorshi.service.dto.TermsAndConditionsDTO;
import org.soptorshi.service.mapper.TermsAndConditionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TermsAndConditions.
 */
@Service
@Transactional
public class TermsAndConditionsService {

    private final Logger log = LoggerFactory.getLogger(TermsAndConditionsService.class);

    private final TermsAndConditionsRepository termsAndConditionsRepository;

    private final TermsAndConditionsMapper termsAndConditionsMapper;

    private final TermsAndConditionsSearchRepository termsAndConditionsSearchRepository;

    public TermsAndConditionsService(TermsAndConditionsRepository termsAndConditionsRepository, TermsAndConditionsMapper termsAndConditionsMapper, TermsAndConditionsSearchRepository termsAndConditionsSearchRepository) {
        this.termsAndConditionsRepository = termsAndConditionsRepository;
        this.termsAndConditionsMapper = termsAndConditionsMapper;
        this.termsAndConditionsSearchRepository = termsAndConditionsSearchRepository;
    }

    /**
     * Save a termsAndConditions.
     *
     * @param termsAndConditionsDTO the entity to save
     * @return the persisted entity
     */
    public TermsAndConditionsDTO save(TermsAndConditionsDTO termsAndConditionsDTO) {
        log.debug("Request to save TermsAndConditions : {}", termsAndConditionsDTO);
        TermsAndConditions termsAndConditions = termsAndConditionsMapper.toEntity(termsAndConditionsDTO);
        termsAndConditions = termsAndConditionsRepository.save(termsAndConditions);
        TermsAndConditionsDTO result = termsAndConditionsMapper.toDto(termsAndConditions);
        termsAndConditionsSearchRepository.save(termsAndConditions);
        return result;
    }

    /**
     * Get all the termsAndConditions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TermsAndConditionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TermsAndConditions");
        return termsAndConditionsRepository.findAll(pageable)
            .map(termsAndConditionsMapper::toDto);
    }


    /**
     * Get one termsAndConditions by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TermsAndConditionsDTO> findOne(Long id) {
        log.debug("Request to get TermsAndConditions : {}", id);
        return termsAndConditionsRepository.findById(id)
            .map(termsAndConditionsMapper::toDto);
    }

    /**
     * Delete the termsAndConditions by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TermsAndConditions : {}", id);
        termsAndConditionsRepository.deleteById(id);
        termsAndConditionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the termsAndConditions corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TermsAndConditionsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TermsAndConditions for query {}", query);
        return termsAndConditionsSearchRepository.search(queryStringQuery(query), pageable)
            .map(termsAndConditionsMapper::toDto);
    }
}
