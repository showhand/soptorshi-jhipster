package org.soptorshi.repository;

import org.soptorshi.domain.AcademicInformationAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AcademicInformationAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicInformationAttachmentRepository extends JpaRepository<AcademicInformationAttachment, Long> {
    List<AcademicInformationAttachment> findByEmployeeId(Long employeeId);
}
