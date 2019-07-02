package org.soptorshi.repository;

import org.soptorshi.domain.AttendanceExcelUpload;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AttendanceExcelUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceExcelUploadRepository extends JpaRepository<AttendanceExcelUpload, Long>, JpaSpecificationExecutor<AttendanceExcelUpload> {

}
