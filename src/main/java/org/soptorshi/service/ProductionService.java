package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Production;
import org.soptorshi.repository.ProductionRepository;
import org.soptorshi.repository.search.ProductionSearchRepository;
import org.soptorshi.service.dto.ProductionDTO;
import org.soptorshi.service.mapper.ProductionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Production.
 */
@Service
@Transactional
public class ProductionService {

    private final Logger log = LoggerFactory.getLogger(ProductionService.class);

    private final ProductionRepository productionRepository;

    private final ProductionMapper productionMapper;

    private final ProductionSearchRepository productionSearchRepository;

    public ProductionService(ProductionRepository productionRepository, ProductionMapper productionMapper, ProductionSearchRepository productionSearchRepository) {
        this.productionRepository = productionRepository;
        this.productionMapper = productionMapper;
        this.productionSearchRepository = productionSearchRepository;
    }

    /**
     * Save a production.
     *
     * @param productionDTO the entity to save
     * @return the persisted entity
     */
    public ProductionDTO save(ProductionDTO productionDTO) {
        log.debug("Request to save Production : {}", productionDTO);
        Production production = productionMapper.toEntity(productionDTO);
        production = productionRepository.save(production);
        ProductionDTO result = productionMapper.toDto(production);
        productionSearchRepository.save(production);
        return result;
    }

    /**
     * Get all the productions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productions");
        return productionRepository.findAll(pageable)
            .map(productionMapper::toDto);
    }


    /**
     * Get one production by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProductionDTO> findOne(Long id) {
        log.debug("Request to get Production : {}", id);
        return productionRepository.findById(id)
            .map(productionMapper::toDto);
    }

    /**
     * Delete the production by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Production : {}", id);
        productionRepository.deleteById(id);
        productionSearchRepository.deleteById(id);
    }

    /**
     * Search for the production corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Productions for query {}", query);
        return productionSearchRepository.search(queryStringQuery(query), pageable)
            .map(productionMapper::toDto);
    }
}
