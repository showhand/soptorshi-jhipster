package org.soptorshi.repository.extended;

import org.soptorshi.repository.CommercialBudgetRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialBudget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialBudgetExtendedRepository extends CommercialBudgetRepository {

    boolean existsByBudgetNo(String BudgetNo);
}
