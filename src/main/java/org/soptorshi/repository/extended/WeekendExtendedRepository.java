package org.soptorshi.repository.extended;

import org.soptorshi.domain.Weekend;
import org.soptorshi.domain.enumeration.WeekendStatus;
import org.soptorshi.repository.WeekendRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Weekend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekendExtendedRepository extends WeekendRepository {

    Optional<Weekend> getByStatus(WeekendStatus weekendStatus);
}
