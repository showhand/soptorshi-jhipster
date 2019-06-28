package org.soptorshi.service;

import org.soptorshi.service.dto.LeaveTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LeaveType.
 */
public interface LeaveTypeService {

    /**
     * Save a leaveType.
     *
     * @param leaveTypeDTO the entity to save
     * @return the persisted entity
     */
    LeaveTypeDTO save(LeaveTypeDTO leaveTypeDTO);

    /**
     * Get all the leaveTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" leaveType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LeaveTypeDTO> findOne(Long id);

    /**
     * Delete the "id" leaveType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the leaveType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveTypeDTO> search(String query, Pageable pageable);
}
