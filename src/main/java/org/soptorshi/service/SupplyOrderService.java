package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyOrder.
 */
public interface SupplyOrderService {

    /**
     * Save a supplyOrder.
     *
     * @param supplyOrderDTO the entity to save
     * @return the persisted entity
     */
    SupplyOrderDTO save(SupplyOrderDTO supplyOrderDTO);

    /**
     * Get all the supplyOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyOrderDTO> findOne(Long id);

    /**
     * Delete the "id" supplyOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyOrder corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyOrderDTO> search(String query, Pageable pageable);
}
