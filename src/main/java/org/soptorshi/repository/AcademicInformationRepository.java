package org.soptorshi.repository;

import org.soptorshi.domain.AcademicInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AcademicInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicInformationRepository extends JpaRepository<AcademicInformation, Long>, JpaSpecificationExecutor<AcademicInformation> {

}
