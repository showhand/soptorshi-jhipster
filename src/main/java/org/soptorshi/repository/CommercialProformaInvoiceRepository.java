package org.soptorshi.repository;

import org.soptorshi.domain.CommercialProformaInvoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialProformaInvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialProformaInvoiceRepository extends JpaRepository<CommercialProformaInvoice, Long>, JpaSpecificationExecutor<CommercialProformaInvoice> {

}
