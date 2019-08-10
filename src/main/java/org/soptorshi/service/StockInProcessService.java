package org.soptorshi.service;

import org.soptorshi.service.dto.StockInProcessDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StockInProcess.
 */
public interface StockInProcessService {

    /**
     * Save a stockInProcess.
     *
     * @param stockInProcessDTO the entity to save
     * @return the persisted entity
     */
    StockInProcessDTO save(StockInProcessDTO stockInProcessDTO);

    /**
     * Get all the stockInProcesses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockInProcessDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stockInProcess.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StockInProcessDTO> findOne(Long id);

    /**
     * Delete the "id" stockInProcess.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the stockInProcess corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockInProcessDTO> search(String query, Pageable pageable);
}
