package org.soptorshi.service;

import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.service.dto.AttendanceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Attendance.
 */
public interface AttendanceService {

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save
     * @return the persisted entity
     */
    AttendanceDTO save(AttendanceDTO attendanceDTO);

    /**
     * Get all the attendances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AttendanceDTO> findAll(Pageable pageable);

    List<AttendanceDTO> getAllByDistinctAttendanceDate();


    /**
     * Get the "id" attendance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AttendanceDTO> findOne(Long id);

    /**
     * Delete the "id" attendance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void deleteByAttendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload);

    /**
     * Search for the attendance corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AttendanceDTO> search(String query, Pageable pageable);
}
