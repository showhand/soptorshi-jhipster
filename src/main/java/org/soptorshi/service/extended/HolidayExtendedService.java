package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Holiday;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.repository.extended.HolidayExtendedRepository;
import org.soptorshi.repository.search.HolidaySearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.HolidayService;
import org.soptorshi.service.dto.HolidayDTO;
import org.soptorshi.service.mapper.HolidayMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HolidayExtendedService extends HolidayService {

    private final Logger log = LoggerFactory.getLogger(HolidayExtendedService.class);

    private final HolidayExtendedRepository holidayExtendedRepository;

    private final HolidayMapper holidayMapper;

    private final HolidaySearchRepository holidaySearchRepository;

    public HolidayExtendedService(HolidayExtendedRepository holidayExtendedRepository, HolidayMapper holidayMapper, HolidaySearchRepository holidaySearchRepository) {
        super(holidayExtendedRepository, holidayMapper, holidaySearchRepository);
        this.holidayExtendedRepository = holidayExtendedRepository;
        this.holidayMapper = holidayMapper;
        this.holidaySearchRepository = holidaySearchRepository;
    }


    /**
     * Save a holiday.
     *
     * @param holidayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HolidayDTO save(HolidayDTO holidayDTO) {
        log.debug("Request to save Holiday : {}", holidayDTO);

        if (holidayDTO.getFromDate().getYear() == holidayDTO.getToDate().getYear()) {
            if(holidayDTO.getToDate().compareTo(holidayDTO.getFromDate()) >= 0) {
                String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
                    SecurityUtils.getCurrentUserLogin().get() : "";
                Instant currentDateTime = Instant.now();

                if (holidayDTO.getId() == null) {
                    holidayDTO.setCreatedBy(currentUser);
                    holidayDTO.setCreatedOn(currentDateTime);
                } else {
                    holidayDTO.setUpdatedBy(currentUser);
                    holidayDTO.setUpdatedOn(currentDateTime);
                }

                holidayDTO.setHolidayYear(holidayDTO.getFromDate().getYear());
                Holiday holiday = holidayMapper.toEntity(holidayDTO);
                holiday = holidayExtendedRepository.save(holiday);
                HolidayDTO result = holidayMapper.toDto(holiday);
                holidaySearchRepository.save(holiday);
                return result;
            }
            throw new BadRequestAlertException("fromDate is greater than toDate year!!", "holiday", "idnull");
        }
        throw new BadRequestAlertException("fromDate and toDate year should be same!!", "holiday", "idnull");
    }

    public ArrayList<LocalDate> getAllHolidayDates(int holidayYear) {
        List<Holiday> holidays = holidayExtendedRepository.getHolidaysByHolidayYear(holidayYear);
        ArrayList<LocalDate> holidayArrayListOfLocalDates = new ArrayList<>();
        for(int i = 0; i < holidays.size(); i++) {
            LocalDate fromDate = holidays.get(i).getFromDate();
            LocalDate toDate = holidays.get(i).getToDate();
            while(fromDate.compareTo(toDate) <= 0) {
                holidayArrayListOfLocalDates.add(fromDate);
                fromDate = fromDate.plusDays(1);
            }
        }
        return holidayArrayListOfLocalDates;
    }

    public ArrayList<LocalDate> getAllHolidayDates(MonthType monthType, int year) {
        ArrayList<LocalDate> holidayDates = new ArrayList<>();
        ArrayList<LocalDate> holidayArrayListOfLocalDates = new ArrayList<>();
        holidayArrayListOfLocalDates = getAllHolidayDates(year);

        for(LocalDate localDate: holidayArrayListOfLocalDates) {
            Month month = localDate.getMonth();
            if(month.getValue() == (monthType.ordinal() + 1)) {
                holidayDates.add(localDate);
            }
        }
        return holidayDates;
    }
}
