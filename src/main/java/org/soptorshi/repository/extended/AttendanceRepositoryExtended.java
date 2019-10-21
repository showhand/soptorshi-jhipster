package org.soptorshi.repository.extended;

import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.repository.AttendanceRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepositoryExtended extends AttendanceRepository {

    List<Attendance> getByAttendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload);

    void deleteByAttendanceExcelUpload(final AttendanceExcelUpload attendanceExcelUpload);

    List<Attendance> getDistinctByAttendanceDateLessThanEqual(LocalDate localDate);
}
