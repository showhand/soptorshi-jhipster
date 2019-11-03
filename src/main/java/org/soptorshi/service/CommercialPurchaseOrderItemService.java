package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPurchaseOrderItem;
import org.soptorshi.repository.CommercialPurchaseOrderItemRepository;
import org.soptorshi.repository.search.CommercialPurchaseOrderItemSearchRepository;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemDTO;
import org.soptorshi.service.mapper.CommercialPurchaseOrderItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPurchaseOrderItem.
 */
@Service
@Transactional
public class CommercialPurchaseOrderItemService {

    private final Logger log = LoggerFactory.getLogger(CommercialPurchaseOrderItemService.class);

    private final CommercialPurchaseOrderItemRepository commercialPurchaseOrderItemRepository;

    private final CommercialPurchaseOrderItemMapper commercialPurchaseOrderItemMapper;

    private final CommercialPurchaseOrderItemSearchRepository commercialPurchaseOrderItemSearchRepository;

    public CommercialPurchaseOrderItemService(CommercialPurchaseOrderItemRepository commercialPurchaseOrderItemRepository, CommercialPurchaseOrderItemMapper commercialPurchaseOrderItemMapper, CommercialPurchaseOrderItemSearchRepository commercialPurchaseOrderItemSearchRepository) {
        this.commercialPurchaseOrderItemRepository = commercialPurchaseOrderItemRepository;
        this.commercialPurchaseOrderItemMapper = commercialPurchaseOrderItemMapper;
        this.commercialPurchaseOrderItemSearchRepository = commercialPurchaseOrderItemSearchRepository;
    }

    /**
     * Save a commercialPurchaseOrderItem.
     *
     * @param commercialPurchaseOrderItemDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPurchaseOrderItemDTO save(CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO) {
        log.debug("Request to save CommercialPurchaseOrderItem : {}", commercialPurchaseOrderItemDTO);
        CommercialPurchaseOrderItem commercialPurchaseOrderItem = commercialPurchaseOrderItemMapper.toEntity(commercialPurchaseOrderItemDTO);
        commercialPurchaseOrderItem = commercialPurchaseOrderItemRepository.save(commercialPurchaseOrderItem);
        CommercialPurchaseOrderItemDTO result = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);
        commercialPurchaseOrderItemSearchRepository.save(commercialPurchaseOrderItem);
        return result;
    }

    /**
     * Get all the commercialPurchaseOrderItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPurchaseOrderItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPurchaseOrderItems");
        return commercialPurchaseOrderItemRepository.findAll(pageable)
            .map(commercialPurchaseOrderItemMapper::toDto);
    }


    /**
     * Get one commercialPurchaseOrderItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPurchaseOrderItemDTO> findOne(Long id) {
        log.debug("Request to get CommercialPurchaseOrderItem : {}", id);
        return commercialPurchaseOrderItemRepository.findById(id)
            .map(commercialPurchaseOrderItemMapper::toDto);
    }

    /**
     * Delete the commercialPurchaseOrderItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPurchaseOrderItem : {}", id);
        commercialPurchaseOrderItemRepository.deleteById(id);
        commercialPurchaseOrderItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialPurchaseOrderItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPurchaseOrderItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPurchaseOrderItems for query {}", query);
        return commercialPurchaseOrderItemSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPurchaseOrderItemMapper::toDto);
    }
}
