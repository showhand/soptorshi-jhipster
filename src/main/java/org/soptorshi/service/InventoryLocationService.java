package org.soptorshi.service;

import org.soptorshi.service.dto.InventoryLocationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing InventoryLocation.
 */
public interface InventoryLocationService {

    /**
     * Save a inventoryLocation.
     *
     * @param inventoryLocationDTO the entity to save
     * @return the persisted entity
     */
    InventoryLocationDTO save(InventoryLocationDTO inventoryLocationDTO);

    /**
     * Get all the inventoryLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InventoryLocationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" inventoryLocation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InventoryLocationDTO> findOne(Long id);

    /**
     * Delete the "id" inventoryLocation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the inventoryLocation corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InventoryLocationDTO> search(String query, Pageable pageable);
}
