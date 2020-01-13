package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Attendance;
import org.soptorshi.repository.AttendanceRepository;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.mapper.AttendanceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Attendance.
 */
@Service
@Transactional
public class AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceSearchRepository attendanceSearchRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, AttendanceSearchRepository attendanceSearchRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.attendanceSearchRepository = attendanceSearchRepository;
    }

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save
     * @return the persisted entity
     */
    public AttendanceDTO save(AttendanceDTO attendanceDTO) {
        log.debug("Request to save Attendance : {}", attendanceDTO);
        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance = attendanceRepository.save(attendance);
        AttendanceDTO result = attendanceMapper.toDto(attendance);
        attendanceSearchRepository.save(attendance);
        return result;
    }

    /**
     * Get all the attendances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attendances");
        return attendanceRepository.findAll(pageable)
            .map(attendanceMapper::toDto);
    }


    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceDTO> findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        return attendanceRepository.findById(id)
            .map(attendanceMapper::toDto);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepository.deleteById(id);
        attendanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the attendance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendanceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Attendances for query {}", query);
        return attendanceSearchRepository.search(queryStringQuery(query), pageable)
            .map(attendanceMapper::toDto);
    }
}
