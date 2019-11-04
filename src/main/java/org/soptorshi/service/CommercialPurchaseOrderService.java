package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.domain.enumeration.CommercialStatus;
import org.soptorshi.repository.CommercialPurchaseOrderRepository;
import org.soptorshi.repository.search.CommercialPurchaseOrderSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.CommercialPoStatusDTO;
import org.soptorshi.service.dto.CommercialPurchaseOrderDTO;
import org.soptorshi.service.mapper.CommercialPurchaseOrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private final CommercialPoStatusService commercialPoStatusService;

    public CommercialPurchaseOrderService(CommercialPurchaseOrderRepository commercialPurchaseOrderRepository, CommercialPurchaseOrderMapper commercialPurchaseOrderMapper, CommercialPurchaseOrderSearchRepository commercialPurchaseOrderSearchRepository,
                                          CommercialPoStatusService commercialPoStatusService) {
        this.commercialPurchaseOrderRepository = commercialPurchaseOrderRepository;
        this.commercialPurchaseOrderMapper = commercialPurchaseOrderMapper;
        this.commercialPurchaseOrderSearchRepository = commercialPurchaseOrderSearchRepository;
        this.commercialPoStatusService = commercialPoStatusService;
    }

    /**
     * Save a commercialPurchaseOrder.
     *
     * @param commercialPurchaseOrderDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public CommercialPurchaseOrderDTO save(CommercialPurchaseOrderDTO commercialPurchaseOrderDTO) {
        log.debug("Request to save CommercialPurchaseOrder : {}", commercialPurchaseOrderDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
        LocalDate currentDate = LocalDate.now();
        if(commercialPurchaseOrderDTO.getId() == null) {
            commercialPurchaseOrderDTO.setCreatedBy(currentUser);
            commercialPurchaseOrderDTO.setCreateOn(currentDate);
        }
        else {
            commercialPurchaseOrderDTO.setUpdatedBy(currentUser);
            commercialPurchaseOrderDTO.setUpdatedOn(currentDate);
        }
        CommercialPurchaseOrder commercialPurchaseOrder = commercialPurchaseOrderMapper.toEntity(commercialPurchaseOrderDTO);
        commercialPurchaseOrder = commercialPurchaseOrderRepository.save(commercialPurchaseOrder);
        if(commercialPurchaseOrderDTO.getId() == null) {
            updateCommercialStatus(commercialPurchaseOrder, currentUser, currentDate);
        }
        CommercialPurchaseOrderDTO result = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);
        commercialPurchaseOrderSearchRepository.save(commercialPurchaseOrder);
        return result;
    }

    private void updateCommercialStatus(CommercialPurchaseOrder commercialPurchaseOrder, String currentUser, LocalDate currentDate) {
        CommercialPoStatusDTO commercialPoStatusDTO = new CommercialPoStatusDTO();
        commercialPoStatusDTO.setStatus(CommercialStatus.WAITING_FOR_PROFORMA_INVOICE);
        commercialPoStatusDTO.setCommercialPurchaseOrderId(commercialPurchaseOrder.getId());
        commercialPoStatusDTO.setCreatedBy(currentUser);
        commercialPoStatusDTO.setCreateOn(currentDate);
        commercialPoStatusService.save(commercialPoStatusDTO);
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
