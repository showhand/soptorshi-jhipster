package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialAttachment;
import org.soptorshi.repository.CommercialAttachmentRepository;
import org.soptorshi.repository.search.CommercialAttachmentSearchRepository;
import org.soptorshi.service.dto.CommercialAttachmentDTO;
import org.soptorshi.service.mapper.CommercialAttachmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialAttachment.
 */
@Service
@Transactional
public class CommercialAttachmentService {

    private final Logger log = LoggerFactory.getLogger(CommercialAttachmentService.class);

    private final CommercialAttachmentRepository commercialAttachmentRepository;

    private final CommercialAttachmentMapper commercialAttachmentMapper;

    private final CommercialAttachmentSearchRepository commercialAttachmentSearchRepository;

    public CommercialAttachmentService(CommercialAttachmentRepository commercialAttachmentRepository, CommercialAttachmentMapper commercialAttachmentMapper, CommercialAttachmentSearchRepository commercialAttachmentSearchRepository) {
        this.commercialAttachmentRepository = commercialAttachmentRepository;
        this.commercialAttachmentMapper = commercialAttachmentMapper;
        this.commercialAttachmentSearchRepository = commercialAttachmentSearchRepository;
    }

    /**
     * Save a commercialAttachment.
     *
     * @param commercialAttachmentDTO the entity to save
     * @return the persisted entity
     */
    public CommercialAttachmentDTO save(CommercialAttachmentDTO commercialAttachmentDTO) {
        log.debug("Request to save CommercialAttachment : {}", commercialAttachmentDTO);
        CommercialAttachment commercialAttachment = commercialAttachmentMapper.toEntity(commercialAttachmentDTO);
        commercialAttachment = commercialAttachmentRepository.save(commercialAttachment);
        CommercialAttachmentDTO result = commercialAttachmentMapper.toDto(commercialAttachment);
        commercialAttachmentSearchRepository.save(commercialAttachment);
        return result;
    }

    /**
     * Get all the commercialAttachments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialAttachments");
        return commercialAttachmentRepository.findAll(pageable)
            .map(commercialAttachmentMapper::toDto);
    }


    /**
     * Get one commercialAttachment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialAttachmentDTO> findOne(Long id) {
        log.debug("Request to get CommercialAttachment : {}", id);
        return commercialAttachmentRepository.findById(id)
            .map(commercialAttachmentMapper::toDto);
    }

    /**
     * Delete the commercialAttachment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialAttachment : {}", id);
        commercialAttachmentRepository.deleteById(id);
        commercialAttachmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialAttachment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialAttachmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialAttachments for query {}", query);
        return commercialAttachmentSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialAttachmentMapper::toDto);
    }
}
