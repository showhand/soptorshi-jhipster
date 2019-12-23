package org.soptorshi.service;

import org.soptorshi.domain.ItemCategory;
import org.soptorshi.repository.ItemCategoryRepository;
import org.soptorshi.repository.search.ItemCategorySearchRepository;
import org.soptorshi.service.dto.ItemCategoryDTO;
import org.soptorshi.service.mapper.ItemCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ItemCategory.
 */
@Service
@Transactional
public class ItemCategoryService {

    private final Logger log = LoggerFactory.getLogger(ItemCategoryService.class);

    private final ItemCategoryRepository itemCategoryRepository;

    private final ItemCategoryMapper itemCategoryMapper;

    private final ItemCategorySearchRepository itemCategorySearchRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository, ItemCategoryMapper itemCategoryMapper, ItemCategorySearchRepository itemCategorySearchRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemCategoryMapper = itemCategoryMapper;
        this.itemCategorySearchRepository = itemCategorySearchRepository;
    }

    /**
     * Save a itemCategory.
     *
     * @param itemCategoryDTO the entity to save
     * @return the persisted entity
     */
    public ItemCategoryDTO save(ItemCategoryDTO itemCategoryDTO) {
        log.debug("Request to save ItemCategory : {}", itemCategoryDTO);
        ItemCategory itemCategory = itemCategoryMapper.toEntity(itemCategoryDTO);
        itemCategory = itemCategoryRepository.save(itemCategory);
        ItemCategoryDTO result = itemCategoryMapper.toDto(itemCategory);
        itemCategorySearchRepository.save(itemCategory);
        return result;
    }

    /**
     * Get all the itemCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ItemCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemCategories");
        return itemCategoryRepository.findAll(pageable)
            .map(itemCategoryMapper::toDto);
    }


    /**
     * Get one itemCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ItemCategoryDTO> findOne(Long id) {
        log.debug("Request to get ItemCategory : {}", id);
        return itemCategoryRepository.findById(id)
            .map(itemCategoryMapper::toDto);
    }

    /**
     * Delete the itemCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemCategory : {}", id);
        itemCategoryRepository.deleteById(id);
        itemCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the itemCategory corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ItemCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemCategories for query {}", query);
        return itemCategorySearchRepository.search(queryStringQuery(query), pageable)
            .map(itemCategoryMapper::toDto);
    }
}
