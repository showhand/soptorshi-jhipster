package org.soptorshi.repository;

import org.soptorshi.domain.ExperienceInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExperienceInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienceInformationRepository extends JpaRepository<ExperienceInformation, Long>, JpaSpecificationExecutor<ExperienceInformation> {

}
