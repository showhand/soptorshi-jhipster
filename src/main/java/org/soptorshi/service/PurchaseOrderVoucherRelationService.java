package org.soptorshi.service;

import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.soptorshi.repository.PurchaseOrderVoucherRelationRepository;
import org.soptorshi.repository.search.PurchaseOrderVoucherRelationSearchRepository;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationDTO;
import org.soptorshi.service.mapper.PurchaseOrderVoucherRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PurchaseOrderVoucherRelation.
 */
@Service
@Transactional
public class PurchaseOrderVoucherRelationService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderVoucherRelationService.class);

    private final PurchaseOrderVoucherRelationRepository purchaseOrderVoucherRelationRepository;

    private final PurchaseOrderVoucherRelationMapper purchaseOrderVoucherRelationMapper;

    private final PurchaseOrderVoucherRelationSearchRepository purchaseOrderVoucherRelationSearchRepository;

    public PurchaseOrderVoucherRelationService(PurchaseOrderVoucherRelationRepository purchaseOrderVoucherRelationRepository, PurchaseOrderVoucherRelationMapper purchaseOrderVoucherRelationMapper, PurchaseOrderVoucherRelationSearchRepository purchaseOrderVoucherRelationSearchRepository) {
        this.purchaseOrderVoucherRelationRepository = purchaseOrderVoucherRelationRepository;
        this.purchaseOrderVoucherRelationMapper = purchaseOrderVoucherRelationMapper;
        this.purchaseOrderVoucherRelationSearchRepository = purchaseOrderVoucherRelationSearchRepository;
    }

    /**
     * Save a purchaseOrderVoucherRelation.
     *
     * @param purchaseOrderVoucherRelationDTO the entity to save
     * @return the persisted entity
     */
    public PurchaseOrderVoucherRelationDTO save(PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO) {
        log.debug("Request to save PurchaseOrderVoucherRelation : {}", purchaseOrderVoucherRelationDTO);
        PurchaseOrderVoucherRelation purchaseOrderVoucherRelation = purchaseOrderVoucherRelationMapper.toEntity(purchaseOrderVoucherRelationDTO);
        purchaseOrderVoucherRelation = purchaseOrderVoucherRelationRepository.save(purchaseOrderVoucherRelation);
        PurchaseOrderVoucherRelationDTO result = purchaseOrderVoucherRelationMapper.toDto(purchaseOrderVoucherRelation);
        purchaseOrderVoucherRelationSearchRepository.save(purchaseOrderVoucherRelation);
        return result;
    }

    /**
     * Get all the purchaseOrderVoucherRelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderVoucherRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrderVoucherRelations");
        return purchaseOrderVoucherRelationRepository.findAll(pageable)
            .map(purchaseOrderVoucherRelationMapper::toDto);
    }


    /**
     * Get one purchaseOrderVoucherRelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PurchaseOrderVoucherRelationDTO> findOne(Long id) {
        log.debug("Request to get PurchaseOrderVoucherRelation : {}", id);
        return purchaseOrderVoucherRelationRepository.findById(id)
            .map(purchaseOrderVoucherRelationMapper::toDto);
    }

    /**
     * Delete the purchaseOrderVoucherRelation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrderVoucherRelation : {}", id);
        purchaseOrderVoucherRelationRepository.deleteById(id);
        purchaseOrderVoucherRelationSearchRepository.deleteById(id);
    }

    /**
     * Search for the purchaseOrderVoucherRelation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderVoucherRelationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PurchaseOrderVoucherRelations for query {}", query);
        return purchaseOrderVoucherRelationSearchRepository.search(queryStringQuery(query), pageable)
            .map(purchaseOrderVoucherRelationMapper::toDto);
    }
}
