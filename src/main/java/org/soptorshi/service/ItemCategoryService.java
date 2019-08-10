package org.soptorshi.service;

import org.soptorshi.service.dto.ItemCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemCategory.
 */
public interface ItemCategoryService {

    /**
     * Save a itemCategory.
     *
     * @param itemCategoryDTO the entity to save
     * @return the persisted entity
     */
    ItemCategoryDTO save(ItemCategoryDTO itemCategoryDTO);

    /**
     * Get all the itemCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" itemCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the itemCategory corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemCategoryDTO> search(String query, Pageable pageable);
}
