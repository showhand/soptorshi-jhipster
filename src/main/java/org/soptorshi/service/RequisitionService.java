package org.soptorshi.service;

import org.soptorshi.domain.Requisition;
import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.search.RequisitionSearchRepository;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.mapper.RequisitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Requisition.
 */
@Service
@Transactional
public class RequisitionService {

    private final Logger log = LoggerFactory.getLogger(RequisitionService.class);

    private final RequisitionRepository requisitionRepository;

    private final RequisitionMapper requisitionMapper;

    private final RequisitionSearchRepository requisitionSearchRepository;

    public RequisitionService(RequisitionRepository requisitionRepository, RequisitionMapper requisitionMapper, RequisitionSearchRepository requisitionSearchRepository) {
        this.requisitionRepository = requisitionRepository;
        this.requisitionMapper = requisitionMapper;
        this.requisitionSearchRepository = requisitionSearchRepository;
    }

    /**
     * Save a requisition.
     *
     * @param requisitionDTO the entity to save
     * @return the persisted entity
     */
    public RequisitionDTO save(RequisitionDTO requisitionDTO) {
        log.debug("Request to save Requisition : {}", requisitionDTO);
        Requisition requisition = requisitionMapper.toEntity(requisitionDTO);
        requisition = requisitionRepository.save(requisition);
        RequisitionDTO result = requisitionMapper.toDto(requisition);
        requisitionSearchRepository.save(requisition);
        return result;
    }

    /**
     * Get all the requisitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requisitions");
        return requisitionRepository.findAll(pageable)
            .map(requisitionMapper::toDto);
    }


    /**
     * Get one requisition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RequisitionDTO> findOne(Long id) {
        log.debug("Request to get Requisition : {}", id);
        return requisitionRepository.findById(id)
            .map(requisitionMapper::toDto);
    }

    /**
     * Delete the requisition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Requisition : {}", id);
        requisitionRepository.deleteById(id);
        requisitionSearchRepository.deleteById(id);
    }

    /**
     * Search for the requisition corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Requisitions for query {}", query);
        return requisitionSearchRepository.search(queryStringQuery(query), pageable)
            .map(requisitionMapper::toDto);
    }
}
