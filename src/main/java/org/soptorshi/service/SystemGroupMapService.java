package org.soptorshi.service;

import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.search.SystemGroupMapSearchRepository;
import org.soptorshi.service.dto.SystemGroupMapDTO;
import org.soptorshi.service.mapper.SystemGroupMapMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SystemGroupMap.
 */
@Service
@Transactional
public class SystemGroupMapService {

    private final Logger log = LoggerFactory.getLogger(SystemGroupMapService.class);

    private final SystemGroupMapRepository systemGroupMapRepository;

    private final SystemGroupMapMapper systemGroupMapMapper;

    private final SystemGroupMapSearchRepository systemGroupMapSearchRepository;

    public SystemGroupMapService(SystemGroupMapRepository systemGroupMapRepository, SystemGroupMapMapper systemGroupMapMapper, SystemGroupMapSearchRepository systemGroupMapSearchRepository) {
        this.systemGroupMapRepository = systemGroupMapRepository;
        this.systemGroupMapMapper = systemGroupMapMapper;
        this.systemGroupMapSearchRepository = systemGroupMapSearchRepository;
    }

    /**
     * Save a systemGroupMap.
     *
     * @param systemGroupMapDTO the entity to save
     * @return the persisted entity
     */
    public SystemGroupMapDTO save(SystemGroupMapDTO systemGroupMapDTO) {
        log.debug("Request to save SystemGroupMap : {}", systemGroupMapDTO);
        SystemGroupMap systemGroupMap = systemGroupMapMapper.toEntity(systemGroupMapDTO);
        systemGroupMap = systemGroupMapRepository.save(systemGroupMap);
        SystemGroupMapDTO result = systemGroupMapMapper.toDto(systemGroupMap);
        systemGroupMapSearchRepository.save(systemGroupMap);
        return result;
    }

    /**
     * Get all the systemGroupMaps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemGroupMapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemGroupMaps");
        return systemGroupMapRepository.findAll(pageable)
            .map(systemGroupMapMapper::toDto);
    }


    /**
     * Get one systemGroupMap by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SystemGroupMapDTO> findOne(Long id) {
        log.debug("Request to get SystemGroupMap : {}", id);
        return systemGroupMapRepository.findById(id)
            .map(systemGroupMapMapper::toDto);
    }

    /**
     * Delete the systemGroupMap by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SystemGroupMap : {}", id);
        systemGroupMapRepository.deleteById(id);
        systemGroupMapSearchRepository.deleteById(id);
    }

    /**
     * Search for the systemGroupMap corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemGroupMapDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SystemGroupMaps for query {}", query);
        return systemGroupMapSearchRepository.search(queryStringQuery(query), pageable)
            .map(systemGroupMapMapper::toDto);
    }
}
