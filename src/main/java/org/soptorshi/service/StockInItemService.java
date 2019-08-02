package org.soptorshi.service;

import org.soptorshi.service.dto.StockInItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StockInItem.
 */
public interface StockInItemService {

    /**
     * Save a stockInItem.
     *
     * @param stockInItemDTO the entity to save
     * @return the persisted entity
     */
    StockInItemDTO save(StockInItemDTO stockInItemDTO);

    /**
     * Get all the stockInItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockInItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stockInItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StockInItemDTO> findOne(Long id);

    /**
     * Delete the "id" stockInItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the stockInItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockInItemDTO> search(String query, Pageable pageable);
}
