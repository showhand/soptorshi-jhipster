package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.repository.SupplyOrderRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyOrder.
 */
@Service
@Transactional
public class SupplyOrderService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderService.class);

    private final SupplyOrderRepository supplyOrderRepository;

    private final SupplyOrderMapper supplyOrderMapper;

    private final SupplyOrderSearchRepository supplyOrderSearchRepository;

    public SupplyOrderService(SupplyOrderRepository supplyOrderRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository) {
        this.supplyOrderRepository = supplyOrderRepository;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyOrderSearchRepository = supplyOrderSearchRepository;
    }

    /**
     * Save a supplyOrder.
     *
     * @param supplyOrderDTO the entity to save
     * @return the persisted entity
     */
    public SupplyOrderDTO save(SupplyOrderDTO supplyOrderDTO) {
        log.debug("Request to save SupplyOrder : {}", supplyOrderDTO);
        SupplyOrder supplyOrder = supplyOrderMapper.toEntity(supplyOrderDTO);
        supplyOrder = supplyOrderRepository.save(supplyOrder);
        SupplyOrderDTO result = supplyOrderMapper.toDto(supplyOrder);
        supplyOrderSearchRepository.save(supplyOrder);
        return result;
    }

    /**
     * Get all the supplyOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyOrders");
        return supplyOrderRepository.findAll(pageable)
            .map(supplyOrderMapper::toDto);
    }


    /**
     * Get one supplyOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyOrderDTO> findOne(Long id) {
        log.debug("Request to get SupplyOrder : {}", id);
        return supplyOrderRepository.findById(id)
            .map(supplyOrderMapper::toDto);
    }

    /**
     * Delete the supplyOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyOrder : {}", id);
        supplyOrderRepository.deleteById(id);
        supplyOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyOrder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyOrderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyOrders for query {}", query);
        return supplyOrderSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyOrderMapper::toDto);
    }
}
