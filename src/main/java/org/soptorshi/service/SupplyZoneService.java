package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyZoneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyZone.
 */
public interface SupplyZoneService {

    /**
     * Save a supplyZone.
     *
     * @param supplyZoneDTO the entity to save
     * @return the persisted entity
     */
    SupplyZoneDTO save(SupplyZoneDTO supplyZoneDTO);

    /**
     * Get all the supplyZones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyZoneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyZone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyZoneDTO> findOne(Long id);

    /**
     * Delete the "id" supplyZone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyZone corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyZoneDTO> search(String query, Pageable pageable);
}
