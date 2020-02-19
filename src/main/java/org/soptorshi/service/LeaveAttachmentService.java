package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.LeaveAttachment;
import org.soptorshi.repository.LeaveAttachmentRepository;
import org.soptorshi.repository.search.LeaveAttachmentSearchRepository;
import org.soptorshi.service.dto.LeaveAttachmentDTO;
import org.soptorshi.service.mapper.LeaveAttachmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing LeaveAttachment.
 */
@Service
@Transactional
public class LeaveAttachmentService {

    private final Logger log = LoggerFactory.getLogger(LeaveAttachmentService.class);

    private final LeaveAttachmentRepository leaveAttachmentRepository;

    private final LeaveAttachmentMapper leaveAttachmentMapper;

    private final LeaveAttachmentSearchRepository leaveAttachmentSearchRepository;

    public LeaveAttachmentService(LeaveAttachmentRepository leaveAttachmentRepository, LeaveAttachmentMapper leaveAttachmentMapper, LeaveAttachmentSearchRepository leaveAttachmentSearchRepository) {
        this.leaveAttachmentRepository = leaveAttachmentRepository;
        this.leaveAttachmentMapper = leaveAttachmentMapper;
        this.leaveAttachmentSearchRepository = leaveAttachmentSearchRepository;
    }

    /**
     * Save a leaveAttachment.
     *
     * @param leaveAttachmentDTO the entity to save
     * @return the persisted entity
     */
    public LeaveAttachmentDTO save(LeaveAttachmentDTO leaveAttachmentDTO) {
        log.debug("Request to save LeaveAttachment : {}", leaveAttachmentDTO);
        LeaveAttachment leaveAttachment = leaveAttachmentMapper.toEntity(leaveAttachmentDTO);
        leaveAttachment = leaveAttachmentRepository.save(leaveAttachment);
        LeaveAttachmentDTO result = leaveAttachmentMapper.toDto(leaveAttachment);
        leaveAttachmentSearchRepository.save(leaveAttachment);
        return result;
    }

    /**
     * Get all the leaveAttachments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaveAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveAttachments");
        return leaveAttachmentRepository.findAll(pageable)
            .map(leaveAttachmentMapper::toDto);
    }


    /**
     * Get one leaveAttachment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LeaveAttachmentDTO> findOne(Long id) {
        log.debug("Request to get LeaveAttachment : {}", id);
        return leaveAttachmentRepository.findById(id)
            .map(leaveAttachmentMapper::toDto);
    }

    /**
     * Delete the leaveAttachment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveAttachment : {}", id);
        leaveAttachmentRepository.deleteById(id);
        leaveAttachmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the leaveAttachment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaveAttachmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeaveAttachments for query {}", query);
        return leaveAttachmentSearchRepository.search(queryStringQuery(query), pageable)
            .map(leaveAttachmentMapper::toDto);
    }
}
