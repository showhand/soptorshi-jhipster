package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.StockInProcess;
import org.soptorshi.repository.StockInProcessRepository;
import org.soptorshi.repository.search.StockInProcessSearchRepository;
import org.soptorshi.service.dto.StockInProcessDTO;
import org.soptorshi.service.mapper.StockInProcessMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing StockInProcess.
 */
@Service
@Transactional
public class StockInProcessService {

    private final Logger log = LoggerFactory.getLogger(StockInProcessService.class);

    private final StockInProcessRepository stockInProcessRepository;

    private final StockInProcessMapper stockInProcessMapper;

    private final StockInProcessSearchRepository stockInProcessSearchRepository;

    public StockInProcessService(StockInProcessRepository stockInProcessRepository, StockInProcessMapper stockInProcessMapper, StockInProcessSearchRepository stockInProcessSearchRepository) {
        this.stockInProcessRepository = stockInProcessRepository;
        this.stockInProcessMapper = stockInProcessMapper;
        this.stockInProcessSearchRepository = stockInProcessSearchRepository;
    }

    /**
     * Save a stockInProcess.
     *
     * @param stockInProcessDTO the entity to save
     * @return the persisted entity
     */
    public StockInProcessDTO save(StockInProcessDTO stockInProcessDTO) {
        log.debug("Request to save StockInProcess : {}", stockInProcessDTO);
        StockInProcess stockInProcess = stockInProcessMapper.toEntity(stockInProcessDTO);
        stockInProcess = stockInProcessRepository.save(stockInProcess);
        StockInProcessDTO result = stockInProcessMapper.toDto(stockInProcess);
        stockInProcessSearchRepository.save(stockInProcess);
        return result;
    }

    /**
     * Get all the stockInProcesses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockInProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockInProcesses");
        return stockInProcessRepository.findAll(pageable)
            .map(stockInProcessMapper::toDto);
    }


    /**
     * Get one stockInProcess by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StockInProcessDTO> findOne(Long id) {
        log.debug("Request to get StockInProcess : {}", id);
        return stockInProcessRepository.findById(id)
            .map(stockInProcessMapper::toDto);
    }

    /**
     * Delete the stockInProcess by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StockInProcess : {}", id);
        stockInProcessRepository.deleteById(id);
        stockInProcessSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockInProcess corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StockInProcessDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockInProcesses for query {}", query);
        return stockInProcessSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockInProcessMapper::toDto);
    }
}
