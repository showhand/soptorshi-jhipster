package org.soptorshi.repository.extended;

import org.soptorshi.repository.HolidayTypeRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HolidayType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayTypeExtendedRepository extends HolidayTypeRepository {

    boolean existsByName(String name);
}
