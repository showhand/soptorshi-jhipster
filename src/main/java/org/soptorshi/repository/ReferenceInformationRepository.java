package org.soptorshi.repository;

import org.soptorshi.domain.ReferenceInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReferenceInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferenceInformationRepository extends JpaRepository<ReferenceInformation, Long>, JpaSpecificationExecutor<ReferenceInformation> {

}
