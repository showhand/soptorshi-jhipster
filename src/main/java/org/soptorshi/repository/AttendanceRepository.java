package org.soptorshi.repository;

import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.service.dto.AttendanceDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

    List<Attendance> getByAttendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload);

    void deleteByAttendanceExcelUpload(final AttendanceExcelUpload attendanceExcelUpload);

    List<Attendance> getDistinctFirstByAttendanceDateLessThan(LocalDate localDate);
}
