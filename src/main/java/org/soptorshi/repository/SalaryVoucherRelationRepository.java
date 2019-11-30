package org.soptorshi.repository;

import org.soptorshi.domain.SalaryVoucherRelation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalaryVoucherRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalaryVoucherRelationRepository extends JpaRepository<SalaryVoucherRelation, Long>, JpaSpecificationExecutor<SalaryVoucherRelation> {

}
