package org.soptorshi.service;

import org.soptorshi.domain.RequisitionMessages;
import org.soptorshi.repository.RequisitionMessagesRepository;
import org.soptorshi.repository.search.RequisitionMessagesSearchRepository;
import org.soptorshi.service.dto.RequisitionMessagesDTO;
import org.soptorshi.service.mapper.RequisitionMessagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RequisitionMessages.
 */
@Service
@Transactional
public class RequisitionMessagesService {

    private final Logger log = LoggerFactory.getLogger(RequisitionMessagesService.class);

    private final RequisitionMessagesRepository requisitionMessagesRepository;

    private final RequisitionMessagesMapper requisitionMessagesMapper;

    private final RequisitionMessagesSearchRepository requisitionMessagesSearchRepository;

    public RequisitionMessagesService(RequisitionMessagesRepository requisitionMessagesRepository, RequisitionMessagesMapper requisitionMessagesMapper, RequisitionMessagesSearchRepository requisitionMessagesSearchRepository) {
        this.requisitionMessagesRepository = requisitionMessagesRepository;
        this.requisitionMessagesMapper = requisitionMessagesMapper;
        this.requisitionMessagesSearchRepository = requisitionMessagesSearchRepository;
    }

    /**
     * Save a requisitionMessages.
     *
     * @param requisitionMessagesDTO the entity to save
     * @return the persisted entity
     */
    public RequisitionMessagesDTO save(RequisitionMessagesDTO requisitionMessagesDTO) {
        log.debug("Request to save RequisitionMessages : {}", requisitionMessagesDTO);
        RequisitionMessages requisitionMessages = requisitionMessagesMapper.toEntity(requisitionMessagesDTO);
        requisitionMessages = requisitionMessagesRepository.save(requisitionMessages);
        RequisitionMessagesDTO result = requisitionMessagesMapper.toDto(requisitionMessages);
        requisitionMessagesSearchRepository.save(requisitionMessages);
        return result;
    }

    /**
     * Get all the requisitionMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionMessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequisitionMessages");
        return requisitionMessagesRepository.findAll(pageable)
            .map(requisitionMessagesMapper::toDto);
    }


    /**
     * Get one requisitionMessages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RequisitionMessagesDTO> findOne(Long id) {
        log.debug("Request to get RequisitionMessages : {}", id);
        return requisitionMessagesRepository.findById(id)
            .map(requisitionMessagesMapper::toDto);
    }

    /**
     * Delete the requisitionMessages by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequisitionMessages : {}", id);
        requisitionMessagesRepository.deleteById(id);
        requisitionMessagesSearchRepository.deleteById(id);
    }

    /**
     * Search for the requisitionMessages corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionMessagesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequisitionMessages for query {}", query);
        return requisitionMessagesSearchRepository.search(queryStringQuery(query), pageable)
            .map(requisitionMessagesMapper::toDto);
    }
}
