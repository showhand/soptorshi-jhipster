package org.soptorshi.service;

import org.soptorshi.domain.PurchaseOrderMessages;
import org.soptorshi.repository.PurchaseOrderMessagesRepository;
import org.soptorshi.repository.search.PurchaseOrderMessagesSearchRepository;
import org.soptorshi.service.dto.PurchaseOrderMessagesDTO;
import org.soptorshi.service.mapper.PurchaseOrderMessagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PurchaseOrderMessages.
 */
@Service
@Transactional
public class PurchaseOrderMessagesService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderMessagesService.class);

    private final PurchaseOrderMessagesRepository purchaseOrderMessagesRepository;

    private final PurchaseOrderMessagesMapper purchaseOrderMessagesMapper;

    private final PurchaseOrderMessagesSearchRepository purchaseOrderMessagesSearchRepository;

    public PurchaseOrderMessagesService(PurchaseOrderMessagesRepository purchaseOrderMessagesRepository, PurchaseOrderMessagesMapper purchaseOrderMessagesMapper, PurchaseOrderMessagesSearchRepository purchaseOrderMessagesSearchRepository) {
        this.purchaseOrderMessagesRepository = purchaseOrderMessagesRepository;
        this.purchaseOrderMessagesMapper = purchaseOrderMessagesMapper;
        this.purchaseOrderMessagesSearchRepository = purchaseOrderMessagesSearchRepository;
    }

    /**
     * Save a purchaseOrderMessages.
     *
     * @param purchaseOrderMessagesDTO the entity to save
     * @return the persisted entity
     */
    public PurchaseOrderMessagesDTO save(PurchaseOrderMessagesDTO purchaseOrderMessagesDTO) {
        log.debug("Request to save PurchaseOrderMessages : {}", purchaseOrderMessagesDTO);
        PurchaseOrderMessages purchaseOrderMessages = purchaseOrderMessagesMapper.toEntity(purchaseOrderMessagesDTO);
        purchaseOrderMessages = purchaseOrderMessagesRepository.save(purchaseOrderMessages);
        PurchaseOrderMessagesDTO result = purchaseOrderMessagesMapper.toDto(purchaseOrderMessages);
        purchaseOrderMessagesSearchRepository.save(purchaseOrderMessages);
        return result;
    }

    /**
     * Get all the purchaseOrderMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderMessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrderMessages");
        return purchaseOrderMessagesRepository.findAll(pageable)
            .map(purchaseOrderMessagesMapper::toDto);
    }


    /**
     * Get one purchaseOrderMessages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PurchaseOrderMessagesDTO> findOne(Long id) {
        log.debug("Request to get PurchaseOrderMessages : {}", id);
        return purchaseOrderMessagesRepository.findById(id)
            .map(purchaseOrderMessagesMapper::toDto);
    }

    /**
     * Delete the purchaseOrderMessages by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrderMessages : {}", id);
        purchaseOrderMessagesRepository.deleteById(id);
        purchaseOrderMessagesSearchRepository.deleteById(id);
    }

    /**
     * Search for the purchaseOrderMessages corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderMessagesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PurchaseOrderMessages for query {}", query);
        return purchaseOrderMessagesSearchRepository.search(queryStringQuery(query), pageable)
            .map(purchaseOrderMessagesMapper::toDto);
    }
}
