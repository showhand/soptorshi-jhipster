package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyShopDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyShop.
 */
public interface SupplyShopService {

    /**
     * Save a supplyShop.
     *
     * @param supplyShopDTO the entity to save
     * @return the persisted entity
     */
    SupplyShopDTO save(SupplyShopDTO supplyShopDTO);

    /**
     * Get all the supplyShops.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyShopDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyShop.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyShopDTO> findOne(Long id);

    /**
     * Delete the "id" supplyShop.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyShop corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyShopDTO> search(String query, Pageable pageable);
}
