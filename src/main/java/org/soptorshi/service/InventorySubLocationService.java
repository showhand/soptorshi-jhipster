package org.soptorshi.service;

import org.soptorshi.service.dto.InventorySubLocationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing InventorySubLocation.
 */
public interface InventorySubLocationService {

    /**
     * Save a inventorySubLocation.
     *
     * @param inventorySubLocationDTO the entity to save
     * @return the persisted entity
     */
    InventorySubLocationDTO save(InventorySubLocationDTO inventorySubLocationDTO);

    /**
     * Get all the inventorySubLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InventorySubLocationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" inventorySubLocation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InventorySubLocationDTO> findOne(Long id);

    /**
     * Delete the "id" inventorySubLocation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the inventorySubLocation corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InventorySubLocationDTO> search(String query, Pageable pageable);
}
