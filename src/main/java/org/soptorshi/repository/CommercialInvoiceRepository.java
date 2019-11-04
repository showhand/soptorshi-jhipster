package org.soptorshi.repository;

import org.soptorshi.domain.CommercialInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialInvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialInvoiceRepository extends JpaRepository<CommercialInvoice, Long>, JpaSpecificationExecutor<CommercialInvoice> {

}
