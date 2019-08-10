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

import org.soptorshi.domain.ItemSubCategory;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ItemSubCategoryRepository;
import org.soptorshi.repository.search.ItemSubCategorySearchRepository;
import org.soptorshi.service.dto.ItemSubCategoryCriteria;
import org.soptorshi.service.dto.ItemSubCategoryDTO;
import org.soptorshi.service.mapper.ItemSubCategoryMapper;

/**
 * Service for executing complex queries for ItemSubCategory entities in the database.
 * The main input is a {@link ItemSubCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemSubCategoryDTO} or a {@link Page} of {@link ItemSubCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemSubCategoryQueryService extends QueryService<ItemSubCategory> {

    private final Logger log = LoggerFactory.getLogger(ItemSubCategoryQueryService.class);

    private final ItemSubCategoryRepository itemSubCategoryRepository;

    private final ItemSubCategoryMapper itemSubCategoryMapper;

    private final ItemSubCategorySearchRepository itemSubCategorySearchRepository;

    public ItemSubCategoryQueryService(ItemSubCategoryRepository itemSubCategoryRepository, ItemSubCategoryMapper itemSubCategoryMapper, ItemSubCategorySearchRepository itemSubCategorySearchRepository) {
        this.itemSubCategoryRepository = itemSubCategoryRepository;
        this.itemSubCategoryMapper = itemSubCategoryMapper;
        this.itemSubCategorySearchRepository = itemSubCategorySearchRepository;
    }

    /**
     * Return a {@link List} of {@link ItemSubCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemSubCategoryDTO> findByCriteria(ItemSubCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemSubCategory> specification = createSpecification(criteria);
        return itemSubCategoryMapper.toDto(itemSubCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemSubCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemSubCategoryDTO> findByCriteria(ItemSubCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemSubCategory> specification = createSpecification(criteria);
        return itemSubCategoryRepository.findAll(specification, page)
            .map(itemSubCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemSubCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemSubCategory> specification = createSpecification(criteria);
        return itemSubCategoryRepository.count(specification);
    }

    /**
     * Function to convert ItemSubCategoryCriteria to a {@link Specification}
     */
    private Specification<ItemSubCategory> createSpecification(ItemSubCategoryCriteria criteria) {
        Specification<ItemSubCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemSubCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ItemSubCategory_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), ItemSubCategory_.shortName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ItemSubCategory_.description));
            }
            if (criteria.getItemCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemCategoriesId(),
                    root -> root.join(ItemSubCategory_.itemCategories, JoinType.LEFT).get(ItemCategory_.id)));
            }
        }
        return specification;
    }
}
