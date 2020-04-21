package org.soptorshi.repository;

import org.soptorshi.domain.SpecialAllowanceTimeLine;
import org.soptorshi.domain.enumeration.MonthType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the SpecialAllowanceTimeLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialAllowanceTimeLineRepository extends JpaRepository<SpecialAllowanceTimeLine, Long>, JpaSpecificationExecutor<SpecialAllowanceTimeLine> {
    List<SpecialAllowanceTimeLine> getByYearAndMonth(Integer year, MonthType month);
}
