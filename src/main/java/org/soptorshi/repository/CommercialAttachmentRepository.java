package org.soptorshi.repository;

import org.soptorshi.domain.CommercialAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialAttachmentRepository extends JpaRepository<CommercialAttachment, Long>, JpaSpecificationExecutor<CommercialAttachment> {

}
