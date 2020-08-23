package org.soptorshi.service;

import org.soptorshi.domain.DepreciationMap;
import org.soptorshi.repository.DepreciationMapRepository;
import org.soptorshi.repository.search.DepreciationMapSearchRepository;
import org.soptorshi.service.dto.DepreciationMapDTO;
import org.soptorshi.service.mapper.DepreciationMapMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DepreciationMap.
 */
@Service
@Transactional
public class DepreciationMapService {

    private final Logger log = LoggerFactory.getLogger(DepreciationMapService.class);

    private final DepreciationMapRepository depreciationMapRepository;

    private final DepreciationMapMapper depreciationMapMapper;

    private final DepreciationMapSearchRepository depreciationMapSearchRepository;

    public DepreciationMapService(DepreciationMapRepository depreciationMapRepository, DepreciationMapMapper depreciationMapMapper, DepreciationMapSearchRepository depreciationMapSearchRepository) {
        this.depreciationMapRepository = depreciationMapRepository;
        this.depreciationMapMapper = depreciationMapMapper;
        this.depreciationMapSearchRepository = depreciationMapSearchRepository;
    }

    /**
     * Save a depreciationMap.
     *
     * @param depreciationMapDTO the entity to save
     * @return the persisted entity
     */
    public DepreciationMapDTO save(DepreciationMapDTO depreciationMapDTO) {
        log.debug("Request to save DepreciationMap : {}", depreciationMapDTO);
        DepreciationMap depreciationMap = depreciationMapMapper.toEntity(depreciationMapDTO);
        depreciationMap = depreciationMapRepository.save(depreciationMap);
        DepreciationMapDTO result = depreciationMapMapper.toDto(depreciationMap);
        depreciationMapSearchRepository.save(depreciationMap);
        return result;
    }

    /**
     * Get all the depreciationMaps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepreciationMapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepreciationMaps");
        return depreciationMapRepository.findAll(pageable)
            .map(depreciationMapMapper::toDto);
    }


    /**
     * Get one depreciationMap by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DepreciationMapDTO> findOne(Long id) {
        log.debug("Request to get DepreciationMap : {}", id);
        return depreciationMapRepository.findById(id)
            .map(depreciationMapMapper::toDto);
    }

    /**
     * Delete the depreciationMap by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DepreciationMap : {}", id);
        depreciationMapRepository.deleteById(id);
        depreciationMapSearchRepository.deleteById(id);
    }

    /**
     * Search for the depreciationMap corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepreciationMapDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DepreciationMaps for query {}", query);
        return depreciationMapSearchRepository.search(queryStringQuery(query), pageable)
            .map(depreciationMapMapper::toDto);
    }
}
