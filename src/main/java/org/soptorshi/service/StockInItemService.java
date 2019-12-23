package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.StockInItem;
import org.soptorshi.repository.StockInItemRepository;
import org.soptorshi.repository.search.StockInItemSearchRepository;
import org.soptorshi.service.dto.StockInItemDTO;
import org.soptorshi.service.mapper.StockInItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing StockInItem.
 */
@Service
@Transactional
public class StockInItemService {

    private final Logger log = LoggerFactory.getLogger(StockInItemService.class);

    private final StockInItemRepository stockInItemRepository;

    private final StockInItemMapper stockInItemMapper;

    private final StockInItemSearchRepository stockInItemSearchRepository;

    public StockInItemService(StockInItemRepository stockInItemRepository, StockInItemMapper stockInItemMapper, StockInItemSearchRepository stockInItemSearchRepository) {
        this.stockInItemRepository = stockInItemRepository;
        this.stockInItemMapper = stockInItemMapper;
        this.stockInItemSearchRepository = stockInItemSearchRepository;
    }

    /**
     * Save a stockInItem.
     *
     * @param stockInItemDTO the entity to save
     * @return the persisted entity
     */
    public StockInItemDTO save(StockInItemDTO stockInItemDTO) {
        log.debug("Request to save StockInItem : {}", stockInItemDTO);
        StockInItem stockInItem = stockInItemMapper.toEntity(stockInItemDTO);
        stockInItem = stockInItemRepository.save(stockInItem);
        StockInItemDTO result = stockInItemMapper.toDto(stockInItem);
        stockInItemSearchRepository.save(stockInItem);
        return result;
    }

    /**
     * Get all the stockInItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockInItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockInItems");
        return stockInItemRepository.findAll(pageable)
            .map(stockInItemMapper::toDto);
    }


    /**
     * Get one stockInItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StockInItemDTO> findOne(Long id) {
        log.debug("Request to get StockInItem : {}", id);
        return stockInItemRepository.findById(id)
            .map(stockInItemMapper::toDto);
    }

    /**
     * Delete the stockInItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StockInItem : {}", id);
        stockInItemRepository.deleteById(id);
        stockInItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockInItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockInItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockInItems for query {}", query);
        return stockInItemSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockInItemMapper::toDto);
    }
}
