package org.soptorshi.service;

import org.soptorshi.domain.CommercialWorkOrder;
import org.soptorshi.repository.CommercialWorkOrderRepository;
import org.soptorshi.repository.search.CommercialWorkOrderSearchRepository;
import org.soptorshi.service.dto.CommercialWorkOrderDTO;
import org.soptorshi.service.mapper.CommercialWorkOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CommercialWorkOrder.
 */
@Service
@Transactional
public class CommercialWorkOrderService {

    private final Logger log = LoggerFactory.getLogger(CommercialWorkOrderService.class);

    private final CommercialWorkOrderRepository commercialWorkOrderRepository;

    private final CommercialWorkOrderMapper commercialWorkOrderMapper;

    private final CommercialWorkOrderSearchRepository commercialWorkOrderSearchRepository;

    public CommercialWorkOrderService(CommercialWorkOrderRepository commercialWorkOrderRepository, CommercialWorkOrderMapper commercialWorkOrderMapper, CommercialWorkOrderSearchRepository commercialWorkOrderSearchRepository) {
        this.commercialWorkOrderRepository = commercialWorkOrderRepository;
        this.commercialWorkOrderMapper = commercialWorkOrderMapper;
        this.commercialWorkOrderSearchRepository = commercialWorkOrderSearchRepository;
    }

    /**
     * Save a commercialWorkOrder.
     *
     * @param commercialWorkOrderDTO the entity to save
     * @return the persisted entity
     */
    public CommercialWorkOrderDTO save(CommercialWorkOrderDTO commercialWorkOrderDTO) {
        log.debug("Request to save CommercialWorkOrder : {}", commercialWorkOrderDTO);
        CommercialWorkOrder commercialWorkOrder = commercialWorkOrderMapper.toEntity(commercialWorkOrderDTO);
        commercialWorkOrder = commercialWorkOrderRepository.save(commercialWorkOrder);
        CommercialWorkOrderDTO result = commercialWorkOrderMapper.toDto(commercialWorkOrder);
        commercialWorkOrderSearchRepository.save(commercialWorkOrder);
        return result;
    }

    /**
     * Get all the commercialWorkOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialWorkOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialWorkOrders");
        return commercialWorkOrderRepository.findAll(pageable)
            .map(commercialWorkOrderMapper::toDto);
    }


    /**
     * Get one commercialWorkOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialWorkOrderDTO> findOne(Long id) {
        log.debug("Request to get CommercialWorkOrder : {}", id);
        return commercialWorkOrderRepository.findById(id)
            .map(commercialWorkOrderMapper::toDto);
    }

    /**
     * Delete the commercialWorkOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialWorkOrder : {}", id);
        commercialWorkOrderRepository.deleteById(id);
        commercialWorkOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialWorkOrder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialWorkOrderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialWorkOrders for query {}", query);
        return commercialWorkOrderSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialWorkOrderMapper::toDto);
    }
}
