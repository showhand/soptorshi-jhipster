package org.soptorshi.service.impl;

import org.soptorshi.domain.ItemSubCategory;
import org.soptorshi.repository.ItemSubCategoryRepository;
import org.soptorshi.repository.search.ItemSubCategorySearchRepository;
import org.soptorshi.service.dto.ItemSubCategoryDTO;
import org.soptorshi.service.mapper.ItemSubCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ItemSubCategory.
 */
@Service
@Transactional
public class ItemSubCategoryServiceImpl {

    private final Logger log = LoggerFactory.getLogger(ItemSubCategoryServiceImpl.class);

    private final ItemSubCategoryRepository itemSubCategoryRepository;

    private final ItemSubCategoryMapper itemSubCategoryMapper;

    private final ItemSubCategorySearchRepository itemSubCategorySearchRepository;

    public ItemSubCategoryServiceImpl(ItemSubCategoryRepository itemSubCategoryRepository, ItemSubCategoryMapper itemSubCategoryMapper, ItemSubCategorySearchRepository itemSubCategorySearchRepository) {
        this.itemSubCategoryRepository = itemSubCategoryRepository;
        this.itemSubCategoryMapper = itemSubCategoryMapper;
        this.itemSubCategorySearchRepository = itemSubCategorySearchRepository;
    }

    /**
     * Save a itemSubCategory.
     *
     * @param itemSubCategoryDTO the entity to save
     * @return the persisted entity
     */

    public ItemSubCategoryDTO save(ItemSubCategoryDTO itemSubCategoryDTO) {
        log.debug("Request to save ItemSubCategory : {}", itemSubCategoryDTO);
        ItemSubCategory itemSubCategory = itemSubCategoryMapper.toEntity(itemSubCategoryDTO);
        itemSubCategory = itemSubCategoryRepository.save(itemSubCategory);
        ItemSubCategoryDTO result = itemSubCategoryMapper.toDto(itemSubCategory);
        itemSubCategorySearchRepository.save(itemSubCategory);
        return result;
    }

    /**
     * Get all the itemSubCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<ItemSubCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemSubCategories");
        return itemSubCategoryRepository.findAll(pageable)
            .map(itemSubCategoryMapper::toDto);
    }


    /**
     * Get one itemSubCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */

    @Transactional(readOnly = true)
    public Optional<ItemSubCategoryDTO> findOne(Long id) {
        log.debug("Request to get ItemSubCategory : {}", id);
        return itemSubCategoryRepository.findById(id)
            .map(itemSubCategoryMapper::toDto);
    }

    /**
     * Delete the itemSubCategory by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        log.debug("Request to delete ItemSubCategory : {}", id);
        itemSubCategoryRepository.deleteById(id);
        itemSubCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the itemSubCategory corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<ItemSubCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemSubCategories for query {}", query);
        return itemSubCategorySearchRepository.search(queryStringQuery(query), pageable)
            .map(itemSubCategoryMapper::toDto);
    }
}
