package org.soptorshi.service;

import org.soptorshi.service.dto.AttendanceExcelUploadDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AttendanceExcelUpload.
 */
public interface AttendanceExcelUploadService {

    /**
     * Save a attendanceExcelUpload.
     *
     * @param attendanceExcelUploadDTO the entity to save
     * @return the persisted entity
     */
    AttendanceExcelUploadDTO save(AttendanceExcelUploadDTO attendanceExcelUploadDTO);

    /**
     * Get all the attendanceExcelUploads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AttendanceExcelUploadDTO> findAll(Pageable pageable);


    /**
     * Get the "id" attendanceExcelUpload.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AttendanceExcelUploadDTO> findOne(Long id);

    /**
     * Delete the "id" attendanceExcelUpload.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the attendanceExcelUpload corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AttendanceExcelUploadDTO> search(String query, Pageable pageable);
}
