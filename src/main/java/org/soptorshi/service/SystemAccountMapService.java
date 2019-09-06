package org.soptorshi.service;

import org.soptorshi.domain.SystemAccountMap;
import org.soptorshi.repository.SystemAccountMapRepository;
import org.soptorshi.repository.search.SystemAccountMapSearchRepository;
import org.soptorshi.service.dto.SystemAccountMapDTO;
import org.soptorshi.service.mapper.SystemAccountMapMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SystemAccountMap.
 */
@Service
@Transactional
public class SystemAccountMapService {

    private final Logger log = LoggerFactory.getLogger(SystemAccountMapService.class);

    private final SystemAccountMapRepository systemAccountMapRepository;

    private final SystemAccountMapMapper systemAccountMapMapper;

    private final SystemAccountMapSearchRepository systemAccountMapSearchRepository;

    public SystemAccountMapService(SystemAccountMapRepository systemAccountMapRepository, SystemAccountMapMapper systemAccountMapMapper, SystemAccountMapSearchRepository systemAccountMapSearchRepository) {
        this.systemAccountMapRepository = systemAccountMapRepository;
        this.systemAccountMapMapper = systemAccountMapMapper;
        this.systemAccountMapSearchRepository = systemAccountMapSearchRepository;
    }

    /**
     * Save a systemAccountMap.
     *
     * @param systemAccountMapDTO the entity to save
     * @return the persisted entity
     */
    public SystemAccountMapDTO save(SystemAccountMapDTO systemAccountMapDTO) {
        log.debug("Request to save SystemAccountMap : {}", systemAccountMapDTO);
        SystemAccountMap systemAccountMap = systemAccountMapMapper.toEntity(systemAccountMapDTO);
        systemAccountMap = systemAccountMapRepository.save(systemAccountMap);
        SystemAccountMapDTO result = systemAccountMapMapper.toDto(systemAccountMap);
        systemAccountMapSearchRepository.save(systemAccountMap);
        return result;
    }

    /**
     * Get all the systemAccountMaps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemAccountMapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemAccountMaps");
        return systemAccountMapRepository.findAll(pageable)
            .map(systemAccountMapMapper::toDto);
    }


    /**
     * Get one systemAccountMap by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SystemAccountMapDTO> findOne(Long id) {
        log.debug("Request to get SystemAccountMap : {}", id);
        return systemAccountMapRepository.findById(id)
            .map(systemAccountMapMapper::toDto);
    }

    /**
     * Delete the systemAccountMap by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SystemAccountMap : {}", id);
        systemAccountMapRepository.deleteById(id);
        systemAccountMapSearchRepository.deleteById(id);
    }

    /**
     * Search for the systemAccountMap corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemAccountMapDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SystemAccountMaps for query {}", query);
        return systemAccountMapSearchRepository.search(queryStringQuery(query), pageable)
            .map(systemAccountMapMapper::toDto);
    }
}
