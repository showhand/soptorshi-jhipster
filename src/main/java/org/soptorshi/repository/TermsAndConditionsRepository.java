package org.soptorshi.repository;

import org.soptorshi.domain.TermsAndConditions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TermsAndConditions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermsAndConditionsRepository extends JpaRepository<TermsAndConditions, Long>, JpaSpecificationExecutor<TermsAndConditions> {

}
