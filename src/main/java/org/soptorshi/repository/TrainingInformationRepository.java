package org.soptorshi.repository;

import org.soptorshi.domain.TrainingInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TrainingInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingInformationRepository extends JpaRepository<TrainingInformation, Long>, JpaSpecificationExecutor<TrainingInformation> {

}
