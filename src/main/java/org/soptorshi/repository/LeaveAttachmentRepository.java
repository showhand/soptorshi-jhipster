package org.soptorshi.repository;

import org.soptorshi.domain.LeaveAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LeaveAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveAttachmentRepository extends JpaRepository<LeaveAttachment, Long>, JpaSpecificationExecutor<LeaveAttachment> {

}
