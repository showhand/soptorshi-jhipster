package org.soptorshi.service;

import org.soptorshi.domain.PredefinedNarration;
import org.soptorshi.repository.PredefinedNarrationRepository;
import org.soptorshi.repository.search.PredefinedNarrationSearchRepository;
import org.soptorshi.service.dto.PredefinedNarrationDTO;
import org.soptorshi.service.mapper.PredefinedNarrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PredefinedNarration.
 */
@Service
@Transactional
public class PredefinedNarrationService {

    private final Logger log = LoggerFactory.getLogger(PredefinedNarrationService.class);

    private final PredefinedNarrationRepository predefinedNarrationRepository;

    private final PredefinedNarrationMapper predefinedNarrationMapper;

    private final PredefinedNarrationSearchRepository predefinedNarrationSearchRepository;

    public PredefinedNarrationService(PredefinedNarrationRepository predefinedNarrationRepository, PredefinedNarrationMapper predefinedNarrationMapper, PredefinedNarrationSearchRepository predefinedNarrationSearchRepository) {
        this.predefinedNarrationRepository = predefinedNarrationRepository;
        this.predefinedNarrationMapper = predefinedNarrationMapper;
        this.predefinedNarrationSearchRepository = predefinedNarrationSearchRepository;
    }

    /**
     * Save a predefinedNarration.
     *
     * @param predefinedNarrationDTO the entity to save
     * @return the persisted entity
     */
    public PredefinedNarrationDTO save(PredefinedNarrationDTO predefinedNarrationDTO) {
        log.debug("Request to save PredefinedNarration : {}", predefinedNarrationDTO);
        PredefinedNarration predefinedNarration = predefinedNarrationMapper.toEntity(predefinedNarrationDTO);
        predefinedNarration = predefinedNarrationRepository.save(predefinedNarration);
        PredefinedNarrationDTO result = predefinedNarrationMapper.toDto(predefinedNarration);
        predefinedNarrationSearchRepository.save(predefinedNarration);
        return result;
    }

    /**
     * Get all the predefinedNarrations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PredefinedNarrationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PredefinedNarrations");
        return predefinedNarrationRepository.findAll(pageable)
            .map(predefinedNarrationMapper::toDto);
    }


    /**
     * Get one predefinedNarration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PredefinedNarrationDTO> findOne(Long id) {
        log.debug("Request to get PredefinedNarration : {}", id);
        return predefinedNarrationRepository.findById(id)
            .map(predefinedNarrationMapper::toDto);
    }

    /**
     * Delete the predefinedNarration by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PredefinedNarration : {}", id);
        predefinedNarrationRepository.deleteById(id);
        predefinedNarrationSearchRepository.deleteById(id);
    }

    /**
     * Search for the predefinedNarration corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PredefinedNarrationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PredefinedNarrations for query {}", query);
        return predefinedNarrationSearchRepository.search(queryStringQuery(query), pageable)
            .map(predefinedNarrationMapper::toDto);
    }
}
