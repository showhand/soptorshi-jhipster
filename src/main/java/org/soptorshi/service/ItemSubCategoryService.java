package org.soptorshi.service;

import org.soptorshi.service.dto.ItemSubCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemSubCategory.
 */
public interface ItemSubCategoryService {

    /**
     * Save a itemSubCategory.
     *
     * @param itemSubCategoryDTO the entity to save
     * @return the persisted entity
     */
    ItemSubCategoryDTO save(ItemSubCategoryDTO itemSubCategoryDTO);

    /**
     * Get all the itemSubCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemSubCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemSubCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemSubCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" itemSubCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the itemSubCategory corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemSubCategoryDTO> search(String query, Pageable pageable);
}
