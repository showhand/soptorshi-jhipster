package org.soptorshi.repository;

import org.soptorshi.domain.TrainingInformation;
import org.soptorshi.domain.TrainingInformationAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TrainingInformationAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingInformationAttachmentRepository extends JpaRepository<TrainingInformationAttachment, Long> {
    List<TrainingInformationAttachment> findByEmployeeId(Long employeeId);
}
