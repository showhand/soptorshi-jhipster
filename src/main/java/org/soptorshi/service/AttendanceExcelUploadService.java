package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.repository.AttendanceExcelUploadRepository;
import org.soptorshi.repository.search.AttendanceExcelUploadSearchRepository;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
import org.soptorshi.service.mapper.AttendanceExcelUploadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing AttendanceExcelUpload.
 */
@Service
@Transactional
public class AttendanceExcelUploadService {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadService.class);

    private final AttendanceExcelUploadRepository attendanceExcelUploadRepository;

    private final AttendanceExcelUploadMapper attendanceExcelUploadMapper;

    private final AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository;

    public AttendanceExcelUploadService(AttendanceExcelUploadRepository attendanceExcelUploadRepository, AttendanceExcelUploadMapper attendanceExcelUploadMapper, AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository) {
        this.attendanceExcelUploadRepository = attendanceExcelUploadRepository;
        this.attendanceExcelUploadMapper = attendanceExcelUploadMapper;
        this.attendanceExcelUploadSearchRepository = attendanceExcelUploadSearchRepository;
    }

    /**
     * Save a attendanceExcelUpload.
     *
     * @param attendanceExcelUploadDTO the entity to save
     * @return the persisted entity
     */
    public AttendanceExcelUploadDTO save(AttendanceExcelUploadDTO attendanceExcelUploadDTO) {
        log.debug("Request to save AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        AttendanceExcelUpload attendanceExcelUpload = attendanceExcelUploadMapper.toEntity(attendanceExcelUploadDTO);
        attendanceExcelUpload = attendanceExcelUploadRepository.save(attendanceExcelUpload);
        AttendanceExcelUploadDTO result = attendanceExcelUploadMapper.toDto(attendanceExcelUpload);
        attendanceExcelUploadSearchRepository.save(attendanceExcelUpload);
        return result;
    }

    /**
     * Get all the attendanceExcelUploads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendanceExcelUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceExcelUploads");
        return attendanceExcelUploadRepository.findAll(pageable)
            .map(attendanceExcelUploadMapper::toDto);
    }


    /**
     * Get one attendanceExcelUpload by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceExcelUploadDTO> findOne(Long id) {
        log.debug("Request to get AttendanceExcelUpload : {}", id);
        return attendanceExcelUploadRepository.findById(id)
            .map(attendanceExcelUploadMapper::toDto);
    }

    /**
     * Delete the attendanceExcelUpload by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendanceExcelUpload : {}", id);
        attendanceExcelUploadRepository.deleteById(id);
        attendanceExcelUploadSearchRepository.deleteById(id);
    }

    /**
     * Search for the attendanceExcelUpload corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendanceExcelUploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttendanceExcelUploads for query {}", query);
        return attendanceExcelUploadSearchRepository.search(queryStringQuery(query), pageable)
            .map(attendanceExcelUploadMapper::toDto);
    }
}
