package org.soptorshi.repository.extended;

import org.soptorshi.domain.Weekend;
import org.soptorshi.domain.enumeration.WeekendStatus;
import org.soptorshi.repository.WeekendRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Weekend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekendExtendedRepository extends WeekendRepository {

    Weekend getByStatus(WeekendStatus weekendStatus);
}
