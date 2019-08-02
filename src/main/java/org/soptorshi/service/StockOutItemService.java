package org.soptorshi.service;

import org.soptorshi.service.dto.StockOutItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StockOutItem.
 */
public interface StockOutItemService {

    /**
     * Save a stockOutItem.
     *
     * @param stockOutItemDTO the entity to save
     * @return the persisted entity
     */
    StockOutItemDTO save(StockOutItemDTO stockOutItemDTO);

    /**
     * Get all the stockOutItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockOutItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stockOutItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StockOutItemDTO> findOne(Long id);

    /**
     * Delete the "id" stockOutItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the stockOutItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockOutItemDTO> search(String query, Pageable pageable);
}
