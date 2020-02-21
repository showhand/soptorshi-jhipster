package org.soptorshi.repository.extended;

import org.soptorshi.domain.Holiday;
import org.soptorshi.repository.HolidayRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Holiday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayExtendedRepository extends HolidayRepository {

    List<Holiday> getHolidaysByHolidayYear(int holidayYear);
}
