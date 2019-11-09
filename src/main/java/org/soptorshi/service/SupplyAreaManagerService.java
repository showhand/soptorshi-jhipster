package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyAreaManager.
 */
public interface SupplyAreaManagerService {

    /**
     * Save a supplyAreaManager.
     *
     * @param supplyAreaManagerDTO the entity to save
     * @return the persisted entity
     */
    SupplyAreaManagerDTO save(SupplyAreaManagerDTO supplyAreaManagerDTO);

    /**
     * Get all the supplyAreaManagers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyAreaManagerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyAreaManager.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyAreaManagerDTO> findOne(Long id);

    /**
     * Delete the "id" supplyAreaManager.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyAreaManager corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyAreaManagerDTO> search(String query, Pageable pageable);
}
