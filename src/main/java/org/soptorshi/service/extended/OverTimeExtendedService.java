package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.OverTime;
import org.soptorshi.repository.extended.OverTimeExtendedRepository;
import org.soptorshi.repository.search.OverTimeSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.OverTimeService;
import org.soptorshi.service.dto.OverTimeDTO;
import org.soptorshi.service.mapper.OverTimeMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            SecurityUtils.getCurrentUserLogin().toString() : "";
        Instant currentDateTime = Instant.now();

        if(overTimeDTO.getId() == null) {
            overTimeDTO.setCreatedBy(currentUser);
            overTimeDTO.setCreatedOn(currentDateTime);
        }
        else {
            overTimeDTO.setUpdatedBy(currentUser);
            overTimeDTO.setUpdatedOn(currentDateTime);
        }

        if(overTimeDTO.getFromTime().isBefore(overTimeDTO.getToTime())) {
            LocalDate from = LocalDateTime.ofInstant(overTimeDTO.getFromTime(), ZoneId.systemDefault()).toLocalDate();
            LocalDate to = LocalDateTime.ofInstant(overTimeDTO.getToTime(), ZoneId.systemDefault()).toLocalDate();

            if (from.isEqual(to) && from.isEqual(overTimeDTO.getOverTimeDate()) && to.isEqual(overTimeDTO.getOverTimeDate())) {
                overTimeDTO.setDuration(getDiff(overTimeDTO.getFromTime(), overTimeDTO.getToTime()).toString());

                OverTime overTime = overTimeMapper.toEntity(overTimeDTO);
                overTime = overTimeExtendedRepository.save(overTime);
                OverTimeDTO result = overTimeMapper.toDto(overTime);
                overTimeSearchRepository.save(overTime);
                return result;
            }
            throw new BadRequestAlertException("Date Mismatch!! Please fix the date", "over-time", "idnull");
        }
        throw new BadRequestAlertException("From to To time is not valid", "over-time", "idnull");
    }

    public Duration getDiff(Instant from, Instant to) {
        return Duration.between(from, to);
    }

    public List<LocalDate> getAllDistinctOverTimeDate() {
        log.debug("Request to get all Distinct OverTime Date");
        List<LocalDate> dates = new ArrayList<>();
        List<OverTime> overTimes = overTimeExtendedRepository.findAll();
        for(OverTime overTime: overTimes) {
            dates.add(overTime.getOverTimeDate());
        }
        return dates.stream().distinct().collect(Collectors.toList());
    }
}
