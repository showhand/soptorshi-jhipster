package org.soptorshi.repository;

import org.soptorshi.domain.CommercialBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialBudget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialBudgetRepository extends JpaRepository<CommercialBudget, Long>, JpaSpecificationExecutor<CommercialBudget> {

}
