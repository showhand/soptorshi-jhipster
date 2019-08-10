package org.soptorshi.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.soptorshi.domain.ItemCategory;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ItemCategoryRepository;
import org.soptorshi.repository.search.ItemCategorySearchRepository;
import org.soptorshi.service.dto.ItemCategoryCriteria;
import org.soptorshi.service.dto.ItemCategoryDTO;
import org.soptorshi.service.mapper.ItemCategoryMapper;

/**
 * Service for executing complex queries for ItemCategory entities in the database.
 * The main input is a {@link ItemCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemCategoryDTO} or a {@link Page} of {@link ItemCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemCategoryQueryService extends QueryService<ItemCategory> {

    private final Logger log = LoggerFactory.getLogger(ItemCategoryQueryService.class);

    private final ItemCategoryRepository itemCategoryRepository;

    private final ItemCategoryMapper itemCategoryMapper;

    private final ItemCategorySearchRepository itemCategorySearchRepository;

    public ItemCategoryQueryService(ItemCategoryRepository itemCategoryRepository, ItemCategoryMapper itemCategoryMapper, ItemCategorySearchRepository itemCategorySearchRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemCategoryMapper = itemCategoryMapper;
        this.itemCategorySearchRepository = itemCategorySearchRepository;
    }

    /**
     * Return a {@link List} of {@link ItemCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemCategoryDTO> findByCriteria(ItemCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemCategory> specification = createSpecification(criteria);
        return itemCategoryMapper.toDto(itemCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCategoryDTO> findByCriteria(ItemCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemCategory> specification = createSpecification(criteria);
        return itemCategoryRepository.findAll(specification, page)
            .map(itemCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemCategory> specification = createSpecification(criteria);
        return itemCategoryRepository.count(specification);
    }

    /**
     * Function to convert ItemCategoryCriteria to a {@link Specification}
     */
    private Specification<ItemCategory> createSpecification(ItemCategoryCriteria criteria) {
        Specification<ItemCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ItemCategory_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), ItemCategory_.shortName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ItemCategory_.description));
            }
        }
        return specification;
    }
}
