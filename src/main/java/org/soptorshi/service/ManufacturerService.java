package org.soptorshi.service;

import org.soptorshi.service.dto.ManufacturerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Manufacturer.
 */
public interface ManufacturerService {

    /**
     * Save a manufacturer.
     *
     * @param manufacturerDTO the entity to save
     * @return the persisted entity
     */
    ManufacturerDTO save(ManufacturerDTO manufacturerDTO);

    /**
     * Get all the manufacturers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ManufacturerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" manufacturer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ManufacturerDTO> findOne(Long id);

    /**
     * Delete the "id" manufacturer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the manufacturer corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ManufacturerDTO> search(String query, Pageable pageable);
}
