package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.OverTime;
import org.soptorshi.repository.extended.OverTimeExtendedRepository;
import org.soptorshi.repository.search.OverTimeSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.OverTimeService;
import org.soptorshi.service.dto.OverTimeDTO;
import org.soptorshi.service.mapper.OverTimeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Service Implementation for managing OverTime.
 */
@Service
@Transactional
public class OverTimeExtendedService extends OverTimeService {

    private final Logger log = LoggerFactory.getLogger(OverTimeExtendedService.class);

    private final OverTimeExtendedRepository overTimeExtendedRepository;

    private final OverTimeMapper overTimeMapper;

    private final OverTimeSearchRepository overTimeSearchRepository;

    public OverTimeExtendedService(OverTimeExtendedRepository overTimeExtendedRepository, OverTimeMapper overTimeMapper, OverTimeSearchRepository overTimeSearchRepository) {
        super(overTimeExtendedRepository, overTimeMapper, overTimeSearchRepository);
        this.overTimeExtendedRepository = overTimeExtendedRepository;
        this.overTimeMapper = overTimeMapper;
        this.overTimeSearchRepository = overTimeSearchRepository;
    }

    /**
     * Save a overTime.
     *
     * @param overTimeDTO the entity to save
     * @return the persisted entity
     */
    public OverTimeDTO save(OverTimeDTO overTimeDTO) {
        log.debug("Request to save OverTime : {}", overTimeDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
            SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (overTimeDTO.getId() == null) {
            overTimeDTO.setCreatedBy(currentUser);
            overTimeDTO.setCreatedOn(currentDateTime);
        } else {
            overTimeDTO.setUpdatedBy(currentUser);
            overTimeDTO.setUpdatedOn(currentDateTime);
        }

        overTimeDTO.setDuration(getDiff(overTimeDTO.getFromTime(), overTimeDTO.getToTime()).toString());
        OverTime overTime = overTimeMapper.toEntity(overTimeDTO);
        overTime = overTimeExtendedRepository.save(overTime);
        OverTimeDTO result = overTimeMapper.toDto(overTime);
        overTimeSearchRepository.save(overTime);
        return result;
    }

    public Duration getDiff(Instant from, Instant to) {
        return Duration.between(from, to);
    }

    public List<OverTime> getOverTimes(LocalDate from, LocalDate to, Employee employee) {
        return overTimeExtendedRepository.getAllByOverTimeDateGreaterThanEqualAndOverTimeDateIsLessThanEqualAndEmployeeEqualsOrderByOverTimeDateDesc(from, to, employee);
    }

    public List<OverTime> getOverTimes(LocalDate from, LocalDate to) {
        return overTimeExtendedRepository.getAllByOverTimeDateGreaterThanEqualAndOverTimeDateIsLessThanEqualOrderByOverTimeDateDesc(from, to);
    }

    public List<OverTime> getOverTimes(Employee employee) {
        return overTimeExtendedRepository.getAllByEmployeeEqualsOrderByOverTimeDateDesc(employee);
    }
}
