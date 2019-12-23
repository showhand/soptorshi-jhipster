package org.soptorshi.service;

import org.soptorshi.domain.InventorySubLocation;
import org.soptorshi.repository.InventorySubLocationRepository;
import org.soptorshi.repository.search.InventorySubLocationSearchRepository;
import org.soptorshi.service.dto.InventorySubLocationDTO;
import org.soptorshi.service.mapper.InventorySubLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing InventorySubLocation.
 */
@Service
@Transactional
public class InventorySubLocationService {

    private final Logger log = LoggerFactory.getLogger(InventorySubLocationService.class);

    private final InventorySubLocationRepository inventorySubLocationRepository;

    private final InventorySubLocationMapper inventorySubLocationMapper;

    private final InventorySubLocationSearchRepository inventorySubLocationSearchRepository;

    public InventorySubLocationService(InventorySubLocationRepository inventorySubLocationRepository, InventorySubLocationMapper inventorySubLocationMapper, InventorySubLocationSearchRepository inventorySubLocationSearchRepository) {
        this.inventorySubLocationRepository = inventorySubLocationRepository;
        this.inventorySubLocationMapper = inventorySubLocationMapper;
        this.inventorySubLocationSearchRepository = inventorySubLocationSearchRepository;
    }

    /**
     * Save a inventorySubLocation.
     *
     * @param inventorySubLocationDTO the entity to save
     * @return the persisted entity
     */
    public InventorySubLocationDTO save(InventorySubLocationDTO inventorySubLocationDTO) {
        log.debug("Request to save InventorySubLocation : {}", inventorySubLocationDTO);
        InventorySubLocation inventorySubLocation = inventorySubLocationMapper.toEntity(inventorySubLocationDTO);
        inventorySubLocation = inventorySubLocationRepository.save(inventorySubLocation);
        InventorySubLocationDTO result = inventorySubLocationMapper.toDto(inventorySubLocation);
        inventorySubLocationSearchRepository.save(inventorySubLocation);
        return result;
    }

    /**
     * Get all the inventorySubLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InventorySubLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InventorySubLocations");
        return inventorySubLocationRepository.findAll(pageable)
            .map(inventorySubLocationMapper::toDto);
    }


    /**
     * Get one inventorySubLocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InventorySubLocationDTO> findOne(Long id) {
        log.debug("Request to get InventorySubLocation : {}", id);
        return inventorySubLocationRepository.findById(id)
            .map(inventorySubLocationMapper::toDto);
    }

    /**
     * Delete the inventorySubLocation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InventorySubLocation : {}", id);
        inventorySubLocationRepository.deleteById(id);
        inventorySubLocationSearchRepository.deleteById(id);
    }

    /**
     * Search for the inventorySubLocation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InventorySubLocationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InventorySubLocations for query {}", query);
        return inventorySubLocationSearchRepository.search(queryStringQuery(query), pageable)
            .map(inventorySubLocationMapper::toDto);
    }
}
