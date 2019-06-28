package org.soptorshi.service;

import org.soptorshi.domain.WorkOrder;
import org.soptorshi.repository.WorkOrderRepository;
import org.soptorshi.repository.search.WorkOrderSearchRepository;
import org.soptorshi.service.dto.WorkOrderDTO;
import org.soptorshi.service.mapper.WorkOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WorkOrder.
 */
@Service
@Transactional
public class WorkOrderService {

    private final Logger log = LoggerFactory.getLogger(WorkOrderService.class);

    private final WorkOrderRepository workOrderRepository;

    private final WorkOrderMapper workOrderMapper;

    private final WorkOrderSearchRepository workOrderSearchRepository;

    public WorkOrderService(WorkOrderRepository workOrderRepository, WorkOrderMapper workOrderMapper, WorkOrderSearchRepository workOrderSearchRepository) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
        this.workOrderSearchRepository = workOrderSearchRepository;
    }

    /**
     * Save a workOrder.
     *
     * @param workOrderDTO the entity to save
     * @return the persisted entity
     */
    public WorkOrderDTO save(WorkOrderDTO workOrderDTO) {
        log.debug("Request to save WorkOrder : {}", workOrderDTO);
        WorkOrder workOrder = workOrderMapper.toEntity(workOrderDTO);
        workOrder = workOrderRepository.save(workOrder);
        WorkOrderDTO result = workOrderMapper.toDto(workOrder);
        workOrderSearchRepository.save(workOrder);
        return result;
    }

    /**
     * Get all the workOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkOrders");
        return workOrderRepository.findAll(pageable)
            .map(workOrderMapper::toDto);
    }


    /**
     * Get one workOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<WorkOrderDTO> findOne(Long id) {
        log.debug("Request to get WorkOrder : {}", id);
        return workOrderRepository.findById(id)
            .map(workOrderMapper::toDto);
    }

    /**
     * Delete the workOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkOrder : {}", id);
        workOrderRepository.deleteById(id);
        workOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the workOrder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkOrderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorkOrders for query {}", query);
        return workOrderSearchRepository.search(queryStringQuery(query), pageable)
            .map(workOrderMapper::toDto);
    }
}
