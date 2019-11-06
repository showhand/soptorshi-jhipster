package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyOrderDetails.
 */
public interface SupplyOrderDetailsService {

    /**
     * Save a supplyOrderDetails.
     *
     * @param supplyOrderDetailsDTO the entity to save
     * @return the persisted entity
     */
    SupplyOrderDetailsDTO save(SupplyOrderDetailsDTO supplyOrderDetailsDTO);

    /**
     * Get all the supplyOrderDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyOrderDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyOrderDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyOrderDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" supplyOrderDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyOrderDetails corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyOrderDetailsDTO> search(String query, Pageable pageable);
}
