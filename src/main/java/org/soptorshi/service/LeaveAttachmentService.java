package org.soptorshi.service;

import org.soptorshi.service.dto.LeaveAttachmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LeaveAttachment.
 */
public interface LeaveAttachmentService {

    /**
     * Save a leaveAttachment.
     *
     * @param leaveAttachmentDTO the entity to save
     * @return the persisted entity
     */
    LeaveAttachmentDTO save(LeaveAttachmentDTO leaveAttachmentDTO);

    /**
     * Get all the leaveAttachments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveAttachmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" leaveAttachment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LeaveAttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" leaveAttachment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the leaveAttachment corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveAttachmentDTO> search(String query, Pageable pageable);
}
