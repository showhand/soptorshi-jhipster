package org.soptorshi.repository;

import org.soptorshi.domain.ExperienceInformationAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ExperienceInformationAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienceInformationAttachmentRepository extends JpaRepository<ExperienceInformationAttachment, Long> {
    List<ExperienceInformationAttachment> findByEmployeeId(Long employeeId);
}
