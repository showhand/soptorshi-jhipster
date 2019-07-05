package org.soptorshi.repository;

import org.soptorshi.domain.QuotationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuotationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationDetailsRepository extends JpaRepository<QuotationDetails, Long>, JpaSpecificationExecutor<QuotationDetails> {

}
