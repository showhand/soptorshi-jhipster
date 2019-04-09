package org.soptorshi.repository;

import org.soptorshi.domain.FamilyInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FamilyInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyInformationRepository extends JpaRepository<FamilyInformation, Long>, JpaSpecificationExecutor<FamilyInformation> {

}
