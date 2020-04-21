package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.OverTime;
import org.soptorshi.repository.OverTimeRepository;
import org.soptorshi.repository.search.OverTimeSearchRepository;
import org.soptorshi.service.dto.OverTimeDTO;
import org.soptorshi.service.mapper.OverTimeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing OverTime.
 */
@Service
@Transactional
public class OverTimeService {

    private final Logger log = LoggerFactory.getLogger(OverTimeService.class);

    private final OverTimeRepository overTimeRepository;

    private final OverTimeMapper overTimeMapper;

    private final OverTimeSearchRepository overTimeSearchRepository;

    public OverTimeService(OverTimeRepository overTimeRepository, OverTimeMapper overTimeMapper, OverTimeSearchRepository overTimeSearchRepository) {
        this.overTimeRepository = overTimeRepository;
        this.overTimeMapper = overTimeMapper;
        this.overTimeSearchRepository = overTimeSearchRepository;
    }

    /**
     * Save a overTime.
     *
     * @param overTimeDTO the entity to save
     * @return the persisted entity
     */
    public OverTimeDTO save(OverTimeDTO overTimeDTO) {
        log.debug("Request to save OverTime : {}", overTimeDTO);
        OverTime overTime = overTimeMapper.toEntity(overTimeDTO);
        overTime = overTimeRepository.save(overTime);
        OverTimeDTO result = overTimeMapper.toDto(overTime);
        overTimeSearchRepository.save(overTime);
        return result;
    }

    /**
     * Get all the overTimes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OverTimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OverTimes");
        return overTimeRepository.findAll(pageable)
            .map(overTimeMapper::toDto);
    }


    /**
     * Get one overTime by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OverTimeDTO> findOne(Long id) {
        log.debug("Request to get OverTime : {}", id);
        return overTimeRepository.findById(id)
            .map(overTimeMapper::toDto);
    }

    /**
     * Delete the overTime by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OverTime : {}", id);
        overTimeRepository.deleteById(id);
        overTimeSearchRepository.deleteById(id);
    }

    /**
     * Search for the overTime corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OverTimeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OverTimes for query {}", query);
        return overTimeSearchRepository.search(queryStringQuery(query), pageable)
            .map(overTimeMapper::toDto);
    }
}
