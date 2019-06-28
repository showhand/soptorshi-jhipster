package org.soptorshi.service;

import org.soptorshi.domain.ProductPrice;
import org.soptorshi.repository.ProductPriceRepository;
import org.soptorshi.repository.search.ProductPriceSearchRepository;
import org.soptorshi.service.dto.ProductPriceDTO;
import org.soptorshi.service.mapper.ProductPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductPrice.
 */
@Service
@Transactional
public class ProductPriceService {

    private final Logger log = LoggerFactory.getLogger(ProductPriceService.class);

    private final ProductPriceRepository productPriceRepository;

    private final ProductPriceMapper productPriceMapper;

    private final ProductPriceSearchRepository productPriceSearchRepository;

    public ProductPriceService(ProductPriceRepository productPriceRepository, ProductPriceMapper productPriceMapper, ProductPriceSearchRepository productPriceSearchRepository) {
        this.productPriceRepository = productPriceRepository;
        this.productPriceMapper = productPriceMapper;
        this.productPriceSearchRepository = productPriceSearchRepository;
    }

    /**
     * Save a productPrice.
     *
     * @param productPriceDTO the entity to save
     * @return the persisted entity
     */
    public ProductPriceDTO save(ProductPriceDTO productPriceDTO) {
        log.debug("Request to save ProductPrice : {}", productPriceDTO);
        ProductPrice productPrice = productPriceMapper.toEntity(productPriceDTO);
        productPrice = productPriceRepository.save(productPrice);
        ProductPriceDTO result = productPriceMapper.toDto(productPrice);
        productPriceSearchRepository.save(productPrice);
        return result;
    }

    /**
     * Get all the productPrices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductPrices");
        return productPriceRepository.findAll(pageable)
            .map(productPriceMapper::toDto);
    }


    /**
     * Get one productPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProductPriceDTO> findOne(Long id) {
        log.debug("Request to get ProductPrice : {}", id);
        return productPriceRepository.findById(id)
            .map(productPriceMapper::toDto);
    }

    /**
     * Delete the productPrice by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductPrice : {}", id);
        productPriceRepository.deleteById(id);
        productPriceSearchRepository.deleteById(id);
    }

    /**
     * Search for the productPrice corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductPriceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductPrices for query {}", query);
        return productPriceSearchRepository.search(queryStringQuery(query), pageable)
            .map(productPriceMapper::toDto);
    }
}
