package org.soptorshi.repository;

import org.soptorshi.domain.SupplySalesRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplySalesRepresentative entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplySalesRepresentativeRepository extends JpaRepository<SupplySalesRepresentative, Long>, JpaSpecificationExecutor<SupplySalesRepresentative> {

}
