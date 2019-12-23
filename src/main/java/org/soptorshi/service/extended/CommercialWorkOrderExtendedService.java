package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialWorkOrder;
import org.soptorshi.domain.enumeration.CommercialStatus;
import org.soptorshi.repository.CommercialPurchaseOrderRepository;
import org.soptorshi.repository.CommercialWorkOrderRepository;
import org.soptorshi.repository.extended.CommercialPoStatusExtendedRepository;
import org.soptorshi.repository.search.CommercialWorkOrderSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPoStatusService;
import org.soptorshi.service.CommercialWorkOrderService;
import org.soptorshi.service.dto.CommercialPoStatusDTO;
import org.soptorshi.service.dto.CommercialWorkOrderDTO;
import org.soptorshi.service.mapper.CommercialWorkOrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialWorkOrder.
 */
@Service
@Transactional
public class CommercialWorkOrderExtendedService extends CommercialWorkOrderService {

    private final Logger log = LoggerFactory.getLogger(CommercialWorkOrderExtendedService.class);

    private final CommercialWorkOrderRepository commercialWorkOrderRepository;

    private final CommercialWorkOrderMapper commercialWorkOrderMapper;

    private final CommercialWorkOrderSearchRepository commercialWorkOrderSearchRepository;

    private final CommercialPoStatusExtendedRepository commercialPoStatusRepository;

    private final CommercialPoStatusService commercialPoStatusService;

    private final CommercialPurchaseOrderRepository commercialPurchaseOrderRepository;

    public CommercialWorkOrderExtendedService(CommercialWorkOrderRepository commercialWorkOrderRepository, CommercialWorkOrderMapper commercialWorkOrderMapper, CommercialWorkOrderSearchRepository commercialWorkOrderSearchRepository, CommercialPoStatusExtendedRepository commercialPoStatusRepository, CommercialPoStatusService commercialPoStatusService, CommercialPurchaseOrderRepository commercialPurchaseOrderRepository) {

        super(commercialWorkOrderRepository, commercialWorkOrderMapper, commercialWorkOrderSearchRepository);
        this.commercialWorkOrderRepository = commercialWorkOrderRepository;
        this.commercialWorkOrderMapper = commercialWorkOrderMapper;
        this.commercialWorkOrderSearchRepository = commercialWorkOrderSearchRepository;
        this.commercialPoStatusRepository = commercialPoStatusRepository;
        this.commercialPoStatusService = commercialPoStatusService;
        this.commercialPurchaseOrderRepository = commercialPurchaseOrderRepository;
    }

    /**
     * Save a commercialWorkOrder.
     *
     * @param commercialWorkOrderDTO the entity to save
     * @return the persisted entity
     */
    public CommercialWorkOrderDTO save(CommercialWorkOrderDTO commercialWorkOrderDTO) {
        log.debug("Request to save CommercialWorkOrder : {}", commercialWorkOrderDTO);
        boolean commercialPoStatus = commercialPoStatusRepository.existsByCommercialPurchaseOrderAndStatus(
            commercialPurchaseOrderRepository.getOne(commercialWorkOrderDTO.getCommercialPurchaseOrderId()),
            CommercialStatus.ARTWORK_OF_PACKAGING_APPROVED_AND_ISSUE_WORK_ORDER_FOR_PACKAGING_ACCESSORIES);

        if(commercialPoStatus) {
            String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
            LocalDate currentDate = LocalDate.now();
            if (commercialWorkOrderDTO.getId() == null) {
                commercialWorkOrderDTO.setCreatedBy(currentUser);
                commercialWorkOrderDTO.setCreatedOn(currentDate);
                updateCommercialStatus(commercialWorkOrderDTO, currentUser, currentDate);
            } else {
                commercialWorkOrderDTO.setUpdatedBy(currentUser);
                commercialWorkOrderDTO.setUpdatedOn(currentDate);
            }
            CommercialWorkOrder commercialWorkOrder = commercialWorkOrderMapper.toEntity(commercialWorkOrderDTO);
            commercialWorkOrder = commercialWorkOrderRepository.save(commercialWorkOrder);
            CommercialWorkOrderDTO result = commercialWorkOrderMapper.toDto(commercialWorkOrder);
            commercialWorkOrderSearchRepository.save(commercialWorkOrder);
            return result;
        }
        else {
            return null;
        }
    }

    private void updateCommercialStatus(CommercialWorkOrderDTO commercialWorkOrderDTO, String currentUser, LocalDate currentDate) {
        CommercialPoStatusDTO commercialPoStatusDTO = new CommercialPoStatusDTO();
        commercialPoStatusDTO.setStatus(CommercialStatus.WORK_ORDER_ISSUED_AND_WAITING_FOR_BILL_OR_INVOICE_FROM_SUPPLIER);
        commercialPoStatusDTO.setCommercialPurchaseOrderId(commercialWorkOrderDTO.getCommercialPurchaseOrderId());
        commercialPoStatusDTO.setCreatedBy(currentUser);
        commercialPoStatusDTO.setCreatedOn(currentDate);
        commercialPoStatusService.save(commercialPoStatusDTO);
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
        /*commercialWorkOrderRepository.deleteById(id);
        commercialWorkOrderSearchRepository.deleteById(id);*/
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
