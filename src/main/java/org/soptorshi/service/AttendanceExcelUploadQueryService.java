package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.domain.AttendanceExcelUpload_;
import org.soptorshi.domain.Attendance_;
import org.soptorshi.repository.AttendanceExcelUploadRepository;
import org.soptorshi.repository.search.AttendanceExcelUploadSearchRepository;
import org.soptorshi.service.dto.AttendanceExcelUploadCriteria;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
import org.soptorshi.service.mapper.AttendanceExcelUploadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for AttendanceExcelUpload entities in the database.
 * The main input is a {@link AttendanceExcelUploadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttendanceExcelUploadDTO} or a {@link Page} of {@link AttendanceExcelUploadDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendanceExcelUploadQueryService extends QueryService<AttendanceExcelUpload> {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadQueryService.class);

    private final AttendanceExcelUploadRepository attendanceExcelUploadRepository;

    private final AttendanceExcelUploadMapper attendanceExcelUploadMapper;

    private final AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository;

    public AttendanceExcelUploadQueryService(AttendanceExcelUploadRepository attendanceExcelUploadRepository, AttendanceExcelUploadMapper attendanceExcelUploadMapper, AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository) {
        this.attendanceExcelUploadRepository = attendanceExcelUploadRepository;
        this.attendanceExcelUploadMapper = attendanceExcelUploadMapper;
        this.attendanceExcelUploadSearchRepository = attendanceExcelUploadSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AttendanceExcelUploadDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceExcelUploadDTO> findByCriteria(AttendanceExcelUploadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AttendanceExcelUpload> specification = createSpecification(criteria);
        return attendanceExcelUploadMapper.toDto(attendanceExcelUploadRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttendanceExcelUploadDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceExcelUploadDTO> findByCriteria(AttendanceExcelUploadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AttendanceExcelUpload> specification = createSpecification(criteria);
        return attendanceExcelUploadRepository.findAll(specification, page)
            .map(attendanceExcelUploadMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttendanceExcelUploadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AttendanceExcelUpload> specification = createSpecification(criteria);
        return attendanceExcelUploadRepository.count(specification);
    }

    /**
     * Function to convert AttendanceExcelUploadCriteria to a {@link Specification}
     */
    private Specification<AttendanceExcelUpload> createSpecification(AttendanceExcelUploadCriteria criteria) {
        Specification<AttendanceExcelUpload> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AttendanceExcelUpload_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), AttendanceExcelUpload_.type));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AttendanceExcelUpload_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), AttendanceExcelUpload_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), AttendanceExcelUpload_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), AttendanceExcelUpload_.updatedOn));
            }
            if (criteria.getAttendanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttendanceId(),
                    root -> root.join(AttendanceExcelUpload_.attendances, JoinType.LEFT).get(Attendance_.id)));
            }
        }
        return specification;
    }
}
