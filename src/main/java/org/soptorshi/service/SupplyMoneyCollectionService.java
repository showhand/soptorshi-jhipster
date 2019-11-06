package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyMoneyCollectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyMoneyCollection.
 */
public interface SupplyMoneyCollectionService {

    /**
     * Save a supplyMoneyCollection.
     *
     * @param supplyMoneyCollectionDTO the entity to save
     * @return the persisted entity
     */
    SupplyMoneyCollectionDTO save(SupplyMoneyCollectionDTO supplyMoneyCollectionDTO);

    /**
     * Get all the supplyMoneyCollections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyMoneyCollectionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyMoneyCollection.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyMoneyCollectionDTO> findOne(Long id);

    /**
     * Delete the "id" supplyMoneyCollection.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyMoneyCollection corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyMoneyCollectionDTO> search(String query, Pageable pageable);
}
