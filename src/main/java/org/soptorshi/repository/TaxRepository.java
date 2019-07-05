package org.soptorshi.repository;

import org.soptorshi.domain.Tax;
import org.soptorshi.domain.enumeration.TaxStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxRepository extends JpaRepository<Tax, Long>, JpaSpecificationExecutor<Tax> {
    Tax findByEmployee_IdAndTaxStatus(Long employeeId, TaxStatus taxStatus);
}
