package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.StockOutItem;
import org.soptorshi.repository.StockOutItemRepository;
import org.soptorshi.repository.search.StockOutItemSearchRepository;
import org.soptorshi.service.dto.StockOutItemDTO;
import org.soptorshi.service.mapper.StockOutItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing StockOutItem.
 */
@Service
@Transactional
public class StockOutItemService {

    private final Logger log = LoggerFactory.getLogger(StockOutItemService.class);

    private final StockOutItemRepository stockOutItemRepository;

    private final StockOutItemMapper stockOutItemMapper;

    private final StockOutItemSearchRepository stockOutItemSearchRepository;

    public StockOutItemService(StockOutItemRepository stockOutItemRepository, StockOutItemMapper stockOutItemMapper, StockOutItemSearchRepository stockOutItemSearchRepository) {
        this.stockOutItemRepository = stockOutItemRepository;
        this.stockOutItemMapper = stockOutItemMapper;
        this.stockOutItemSearchRepository = stockOutItemSearchRepository;
    }

    /**
     * Save a stockOutItem.
     *
     * @param stockOutItemDTO the entity to save
     * @return the persisted entity
     */
    public StockOutItemDTO save(StockOutItemDTO stockOutItemDTO) {
        log.debug("Request to save StockOutItem : {}", stockOutItemDTO);
        StockOutItem stockOutItem = stockOutItemMapper.toEntity(stockOutItemDTO);
        stockOutItem = stockOutItemRepository.save(stockOutItem);
        StockOutItemDTO result = stockOutItemMapper.toDto(stockOutItem);
        stockOutItemSearchRepository.save(stockOutItem);
        return result;
    }

    /**
     * Get all the stockOutItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockOutItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockOutItems");
        return stockOutItemRepository.findAll(pageable)
            .map(stockOutItemMapper::toDto);
    }


    /**
     * Get one stockOutItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StockOutItemDTO> findOne(Long id) {
        log.debug("Request to get StockOutItem : {}", id);
        return stockOutItemRepository.findById(id)
            .map(stockOutItemMapper::toDto);
    }

    /**
     * Delete the stockOutItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StockOutItem : {}", id);
        stockOutItemRepository.deleteById(id);
        stockOutItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockOutItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockOutItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockOutItems for query {}", query);
        return stockOutItemSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockOutItemMapper::toDto);
    }
}
