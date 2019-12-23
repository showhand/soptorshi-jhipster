package org.soptorshi.service;

import org.soptorshi.domain.StockStatus;
import org.soptorshi.repository.StockStatusRepository;
import org.soptorshi.repository.search.StockStatusSearchRepository;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.mapper.StockStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StockStatus.
 */
@Service
@Transactional
public class StockStatusService {

    private final Logger log = LoggerFactory.getLogger(StockStatusService.class);

    private final StockStatusRepository stockStatusRepository;

    private final StockStatusMapper stockStatusMapper;

    private final StockStatusSearchRepository stockStatusSearchRepository;

    public StockStatusService(StockStatusRepository stockStatusRepository, StockStatusMapper stockStatusMapper, StockStatusSearchRepository stockStatusSearchRepository) {
        this.stockStatusRepository = stockStatusRepository;
        this.stockStatusMapper = stockStatusMapper;
        this.stockStatusSearchRepository = stockStatusSearchRepository;
    }

    /**
     * Save a stockStatus.
     *
     * @param stockStatusDTO the entity to save
     * @return the persisted entity
     */
    public StockStatusDTO save(StockStatusDTO stockStatusDTO) {
        log.debug("Request to save StockStatus : {}", stockStatusDTO);
        StockStatus stockStatus = stockStatusMapper.toEntity(stockStatusDTO);
        stockStatus = stockStatusRepository.save(stockStatus);
        StockStatusDTO result = stockStatusMapper.toDto(stockStatus);
        stockStatusSearchRepository.save(stockStatus);
        return result;
    }

    /**
     * Get all the stockStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockStatuses");
        return stockStatusRepository.findAll(pageable)
            .map(stockStatusMapper::toDto);
    }


    /**
     * Get one stockStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StockStatusDTO> findOne(Long id) {
        log.debug("Request to get StockStatus : {}", id);
        return stockStatusRepository.findById(id)
            .map(stockStatusMapper::toDto);
    }

    /**
     * Delete the stockStatus by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StockStatus : {}", id);
        stockStatusRepository.deleteById(id);
        stockStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockStatus corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockStatuses for query {}", query);
        return stockStatusSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockStatusMapper::toDto);
    }
}
