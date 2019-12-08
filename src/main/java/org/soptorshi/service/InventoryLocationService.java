package org.soptorshi.service;

import org.soptorshi.domain.InventoryLocation;
import org.soptorshi.repository.InventoryLocationRepository;
import org.soptorshi.repository.search.InventoryLocationSearchRepository;
import org.soptorshi.service.dto.InventoryLocationDTO;
import org.soptorshi.service.mapper.InventoryLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing InventoryLocation.
 */
@Service
@Transactional
public class InventoryLocationService {

    private final Logger log = LoggerFactory.getLogger(InventoryLocationService.class);

    private final InventoryLocationRepository inventoryLocationRepository;

    private final InventoryLocationMapper inventoryLocationMapper;

    private final InventoryLocationSearchRepository inventoryLocationSearchRepository;

    public InventoryLocationService(InventoryLocationRepository inventoryLocationRepository, InventoryLocationMapper inventoryLocationMapper, InventoryLocationSearchRepository inventoryLocationSearchRepository) {
        this.inventoryLocationRepository = inventoryLocationRepository;
        this.inventoryLocationMapper = inventoryLocationMapper;
        this.inventoryLocationSearchRepository = inventoryLocationSearchRepository;
    }

    /**
     * Save a inventoryLocation.
     *
     * @param inventoryLocationDTO the entity to save
     * @return the persisted entity
     */
    public InventoryLocationDTO save(InventoryLocationDTO inventoryLocationDTO) {
        log.debug("Request to save InventoryLocation : {}", inventoryLocationDTO);
        InventoryLocation inventoryLocation = inventoryLocationMapper.toEntity(inventoryLocationDTO);
        inventoryLocation = inventoryLocationRepository.save(inventoryLocation);
        InventoryLocationDTO result = inventoryLocationMapper.toDto(inventoryLocation);
        inventoryLocationSearchRepository.save(inventoryLocation);
        return result;
    }

    /**
     * Get all the inventoryLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InventoryLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryLocations");
        return inventoryLocationRepository.findAll(pageable)
            .map(inventoryLocationMapper::toDto);
    }


    /**
     * Get one inventoryLocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InventoryLocationDTO> findOne(Long id) {
        log.debug("Request to get InventoryLocation : {}", id);
        return inventoryLocationRepository.findById(id)
            .map(inventoryLocationMapper::toDto);
    }

    /**
     * Delete the inventoryLocation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryLocation : {}", id);
        inventoryLocationRepository.deleteById(id);
        inventoryLocationSearchRepository.deleteById(id);
    }

    /**
     * Search for the inventoryLocation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InventoryLocationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InventoryLocations for query {}", query);
        return inventoryLocationSearchRepository.search(queryStringQuery(query), pageable)
            .map(inventoryLocationMapper::toDto);
    }
}
