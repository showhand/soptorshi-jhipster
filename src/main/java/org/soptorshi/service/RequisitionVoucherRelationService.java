package org.soptorshi.service;

import org.soptorshi.domain.RequisitionVoucherRelation;
import org.soptorshi.repository.RequisitionVoucherRelationRepository;
import org.soptorshi.repository.search.RequisitionVoucherRelationSearchRepository;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;
import org.soptorshi.service.mapper.RequisitionVoucherRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RequisitionVoucherRelation.
 */
@Service
@Transactional
public class RequisitionVoucherRelationService {

    private final Logger log = LoggerFactory.getLogger(RequisitionVoucherRelationService.class);

    private final RequisitionVoucherRelationRepository requisitionVoucherRelationRepository;

    private final RequisitionVoucherRelationMapper requisitionVoucherRelationMapper;

    private final RequisitionVoucherRelationSearchRepository requisitionVoucherRelationSearchRepository;

    public RequisitionVoucherRelationService(RequisitionVoucherRelationRepository requisitionVoucherRelationRepository, RequisitionVoucherRelationMapper requisitionVoucherRelationMapper, RequisitionVoucherRelationSearchRepository requisitionVoucherRelationSearchRepository) {
        this.requisitionVoucherRelationRepository = requisitionVoucherRelationRepository;
        this.requisitionVoucherRelationMapper = requisitionVoucherRelationMapper;
        this.requisitionVoucherRelationSearchRepository = requisitionVoucherRelationSearchRepository;
    }

    /**
     * Save a requisitionVoucherRelation.
     *
     * @param requisitionVoucherRelationDTO the entity to save
     * @return the persisted entity
     */
    public RequisitionVoucherRelationDTO save(RequisitionVoucherRelationDTO requisitionVoucherRelationDTO) {
        log.debug("Request to save RequisitionVoucherRelation : {}", requisitionVoucherRelationDTO);
        RequisitionVoucherRelation requisitionVoucherRelation = requisitionVoucherRelationMapper.toEntity(requisitionVoucherRelationDTO);
        requisitionVoucherRelation = requisitionVoucherRelationRepository.save(requisitionVoucherRelation);
        RequisitionVoucherRelationDTO result = requisitionVoucherRelationMapper.toDto(requisitionVoucherRelation);
        requisitionVoucherRelationSearchRepository.save(requisitionVoucherRelation);
        return result;
    }

    /**
     * Get all the requisitionVoucherRelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionVoucherRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequisitionVoucherRelations");
        return requisitionVoucherRelationRepository.findAll(pageable)
            .map(requisitionVoucherRelationMapper::toDto);
    }


    /**
     * Get one requisitionVoucherRelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RequisitionVoucherRelationDTO> findOne(Long id) {
        log.debug("Request to get RequisitionVoucherRelation : {}", id);
        return requisitionVoucherRelationRepository.findById(id)
            .map(requisitionVoucherRelationMapper::toDto);
    }

    /**
     * Delete the requisitionVoucherRelation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequisitionVoucherRelation : {}", id);
        requisitionVoucherRelationRepository.deleteById(id);
        requisitionVoucherRelationSearchRepository.deleteById(id);
    }

    /**
     * Search for the requisitionVoucherRelation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionVoucherRelationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequisitionVoucherRelations for query {}", query);
        return requisitionVoucherRelationSearchRepository.search(queryStringQuery(query), pageable)
            .map(requisitionVoucherRelationMapper::toDto);
    }
}
