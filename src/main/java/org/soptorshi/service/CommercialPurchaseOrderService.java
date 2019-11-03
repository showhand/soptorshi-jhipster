package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.repository.CommercialPurchaseOrderRepository;
import org.soptorshi.repository.search.CommercialPurchaseOrderSearchRepository;
import org.soptorshi.service.dto.CommercialPurchaseOrderDTO;
import org.soptorshi.service.mapper.CommercialPurchaseOrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPurchaseOrder.
 */
@Service
@Transactional
public class CommercialPurchaseOrderService {

    private final Logger log = LoggerFactory.getLogger(CommercialPurchaseOrderService.class);

    private final CommercialPurchaseOrderRepository commercialPurchaseOrderRepository;

    private final CommercialPurchaseOrderMapper commercialPurchaseOrderMapper;

    private final CommercialPurchaseOrderSearchRepository commercialPurchaseOrderSearchRepository;

    public CommercialPurchaseOrderService(CommercialPurchaseOrderRepository commercialPurchaseOrderRepository, CommercialPurchaseOrderMapper commercialPurchaseOrderMapper, CommercialPurchaseOrderSearchRepository commercialPurchaseOrderSearchRepository) {
        this.commercialPurchaseOrderRepository = commercialPurchaseOrderRepository;
        this.commercialPurchaseOrderMapper = commercialPurchaseOrderMapper;
        this.commercialPurchaseOrderSearchRepository = commercialPurchaseOrderSearchRepository;
    }

    /**
     * Save a commercialPurchaseOrder.
     *
     * @param commercialPurchaseOrderDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPurchaseOrderDTO save(CommercialPurchaseOrderDTO commercialPurchaseOrderDTO) {
        log.debug("Request to save CommercialPurchaseOrder : {}", commercialPurchaseOrderDTO);
        CommercialPurchaseOrder commercialPurchaseOrder = commercialPurchaseOrderMapper.toEntity(commercialPurchaseOrderDTO);
        commercialPurchaseOrder = commercialPurchaseOrderRepository.save(commercialPurchaseOrder);
        CommercialPurchaseOrderDTO result = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);
        commercialPurchaseOrderSearchRepository.save(commercialPurchaseOrder);
        return result;
    }

    /**
     * Get all the commercialPurchaseOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPurchaseOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPurchaseOrders");
        return commercialPurchaseOrderRepository.findAll(pageable)
            .map(commercialPurchaseOrderMapper::toDto);
    }


    /**
     * Get one commercialPurchaseOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPurchaseOrderDTO> findOne(Long id) {
        log.debug("Request to get CommercialPurchaseOrder : {}", id);
        return commercialPurchaseOrderRepository.findById(id)
            .map(commercialPurchaseOrderMapper::toDto);
    }

    /**
     * Delete the commercialPurchaseOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPurchaseOrder : {}", id);
        commercialPurchaseOrderRepository.deleteById(id);
        commercialPurchaseOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialPurchaseOrder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPurchaseOrderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPurchaseOrders for query {}", query);
        return commercialPurchaseOrderSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPurchaseOrderMapper::toDto);
    }
}
