package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.StockStatus;
import org.soptorshi.repository.extended.StockStatusExtendedRepository;
import org.soptorshi.repository.search.StockStatusSearchRepository;
import org.soptorshi.service.StockStatusService;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.mapper.StockStatusMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Service
@Transactional
public class StockStatusExtendedService extends StockStatusService {

    private final Logger log = LoggerFactory.getLogger(StockStatusExtendedService.class);

    private final StockStatusMapper stockStatusMapper;

    private final StockStatusSearchRepository stockStatusSearchRepository;

    private final StockStatusExtendedRepository stockStatusExtendedRepository;

    public StockStatusExtendedService(StockStatusExtendedRepository stockStatusExtendedRepository, StockStatusMapper stockStatusMapper, StockStatusSearchRepository stockStatusSearchRepository) {
        super(stockStatusExtendedRepository, stockStatusMapper, stockStatusSearchRepository);
        this.stockStatusExtendedRepository = stockStatusExtendedRepository;
        this.stockStatusMapper = stockStatusMapper;
        this.stockStatusSearchRepository = stockStatusSearchRepository;
    }

    /**
     * Save a stockStatus.
     *
     * @param stockStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockStatusDTO save(StockStatusDTO stockStatusDTO) {
        log.debug("Request to save StockStatus : {}", stockStatusDTO);
        StockStatus stockStatus = stockStatusMapper.toEntity(stockStatusDTO);
        stockStatus = stockStatusExtendedRepository.save(stockStatus);
        /*stockStatusSearchRepository.save(stockStatus);*/
        return stockStatusMapper.toDto(stockStatus);
    }

    /**
     * Get all the stockStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockStatuses");
        return stockStatusExtendedRepository.findAll(pageable)
            .map(stockStatusMapper::toDto);
    }


    /**
     * Get one stockStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockStatusDTO> findOne(Long id) {
        log.debug("Request to get StockStatus : {}", id);
        return stockStatusExtendedRepository.findById(id)
            .map(stockStatusMapper::toDto);
    }

    /**
     * Delete the stockStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockStatus : {}", id);
        stockStatusExtendedRepository.deleteById(id);
        stockStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockStatus corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockStatuses for query {}", query);
        return stockStatusSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockStatusMapper::toDto);
    }
}

