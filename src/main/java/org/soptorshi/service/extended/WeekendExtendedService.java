package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Weekend;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.WeekendStatus;
import org.soptorshi.repository.extended.WeekendExtendedRepository;
import org.soptorshi.repository.search.WeekendSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.WeekendService;
import org.soptorshi.service.dto.WeekendDTO;
import org.soptorshi.service.mapper.WeekendMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Service Implementation for managing Weekend.
 */
@Service
@Transactional
public class WeekendExtendedService extends WeekendService {

    private final Logger log = LoggerFactory.getLogger(WeekendExtendedService.class);

    private final WeekendExtendedRepository weekendExtendedRepository;

    private final WeekendMapper weekendMapper;

    private final WeekendSearchRepository weekendSearchRepository;

    public WeekendExtendedService(WeekendExtendedRepository weekendExtendedRepository, WeekendMapper weekendMapper, WeekendSearchRepository weekendSearchRepository) {
        super(weekendExtendedRepository, weekendMapper, weekendSearchRepository);
        this.weekendExtendedRepository = weekendExtendedRepository;
        this.weekendMapper = weekendMapper;
        this.weekendSearchRepository = weekendSearchRepository;
    }

    /**
     * Save a weekend.
     *
     * @param weekendDTO the entity to save
     * @return the persisted entity
     */
    public WeekendDTO save(WeekendDTO weekendDTO) {
        log.debug("Request to save Weekend : {}", weekendDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
            SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        List<Weekend> weekends = weekendExtendedRepository.findAll();

        if (weekendDTO.getId() == null) {
            weekendDTO.setCreatedBy(currentUser);
            weekendDTO.setCreatedOn(currentDateTime);
        } else {
            weekends.removeIf(weekend -> weekend.getId().equals(weekendDTO.getId()));
            weekendDTO.setUpdatedBy(currentUser);
            weekendDTO.setUpdatedOn(currentDateTime);
        }
        if (hasActiveWeekend(weekends)) {
            throw new BadRequestAlertException("There is an active weekend!!", "weekend", "idnull");
        }
        if (weekendDTO.getNumberOfDays() == 1) {
            if (weekendDTO.getDay1() == null) {
                throw new BadRequestAlertException("Day1 not selected", "weekend", "idnull");
            }
            weekendDTO.setDay2(null);
            weekendDTO.setDay3(null);
        }
        else if (weekendDTO.getNumberOfDays() == 2) {
            if (weekendDTO.getDay1() == null) {
                throw new BadRequestAlertException("Day1 not selected", "weekend", "idnull");
            }
            if (weekendDTO.getDay2() == null) {
                throw new BadRequestAlertException("Day2 not selected", "weekend", "idnull");
            }
            if(weekendDTO.getDay1().equals(weekendDTO.getDay2())) {
                throw new BadRequestAlertException("Same days selected", "weekend", "idnull");
            }
            weekendDTO.setDay3(null);
        }
        else if (weekendDTO.getNumberOfDays() == 3) {
            if (weekendDTO.getDay1() == null) {
                throw new BadRequestAlertException("Day1 not selected", "weekend", "idnull");
            }
            if (weekendDTO.getDay2() == null) {
                throw new BadRequestAlertException("Day2 not selected", "weekend", "idnull");
            }
            if (weekendDTO.getDay3() == null) {
                throw new BadRequestAlertException("Day3 not selected", "weekend", "idnull");
            }
            if(weekendDTO.getDay1().equals(weekendDTO.getDay2()) || weekendDTO.getDay1().equals(weekendDTO.getDay3()) || weekendDTO.getDay2().equals(weekendDTO.getDay3())) {
                throw new BadRequestAlertException("Same days selected", "weekend", "idnull");
            }
        }

        Weekend weekend = weekendMapper.toEntity(weekendDTO);
        weekend = weekendExtendedRepository.save(weekend);
        WeekendDTO result = weekendMapper.toDto(weekend);
        weekendSearchRepository.save(weekend);
        return result;
    }

    public Optional<Weekend> getWeekendByStatus(WeekendStatus weekendStatus) {
        return weekendExtendedRepository.getByStatus(weekendStatus);
    }

    private boolean hasActiveWeekend(List<Weekend> weekends) {
        boolean hasActiveWeekendStatus = false;
        for (Weekend weekend : weekends) {
            if (weekend.getStatus().equals(WeekendStatus.ACTIVE)) {
                hasActiveWeekendStatus = true;
                break;
            }
        }
        return hasActiveWeekendStatus;
    }

    public List<LocalDate> getAllWeekendDates(MonthType monthType, int year) {

        List<LocalDate> localDates = new ArrayList<>();

        YearMonth yearMonth = YearMonth.of(year, monthType.ordinal() + 1);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        LocalDate lastOfMonth = yearMonth.atEndOfMonth();

        Optional<Weekend> weekend = getWeekendByStatus(WeekendStatus.ACTIVE);
        if (weekend.isPresent()) {

            while (firstOfMonth.isBefore(lastOfMonth)) {
                DayOfWeek dayOfWeek = firstOfMonth.getDayOfWeek();
                String day = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).trim().toUpperCase();

                if (weekend.get().getDay1() != null && day.equalsIgnoreCase(weekend.get().getDay1().toString().trim().toUpperCase())) {
                    localDates.add(firstOfMonth);
                }
                if (weekend.get().getDay2() != null && day.equalsIgnoreCase(weekend.get().getDay2().toString().trim().toUpperCase())) {
                    localDates.add(firstOfMonth);
                }
                if (weekend.get().getDay3() != null && day.equalsIgnoreCase(weekend.get().getDay3().toString().trim().toUpperCase())) {
                    localDates.add(firstOfMonth);
                }
                firstOfMonth = firstOfMonth.plusDays(1);
            }

        }
        return localDates;
    }

    public List<Weekend> getAll() {
        return weekendExtendedRepository.findAll();
    }


}
