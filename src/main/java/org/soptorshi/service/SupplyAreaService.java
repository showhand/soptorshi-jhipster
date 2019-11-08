package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyAreaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyArea.
 */
public interface SupplyAreaService {

    /**
     * Save a supplyArea.
     *
     * @param supplyAreaDTO the entity to save
     * @return the persisted entity
     */
    SupplyAreaDTO save(SupplyAreaDTO supplyAreaDTO);

    /**
     * Get all the supplyAreas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyAreaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyArea.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyAreaDTO> findOne(Long id);

    /**
     * Delete the "id" supplyArea.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyArea corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyAreaDTO> search(String query, Pageable pageable);
}
