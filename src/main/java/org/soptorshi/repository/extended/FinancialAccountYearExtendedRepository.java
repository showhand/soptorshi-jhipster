package org.soptorshi.repository.extended;

import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.repository.FinancialAccountYearRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialAccountYearExtendedRepository extends FinancialAccountYearRepository {
    FinancialAccountYear getByStatus(FinancialYearStatus status);
}
