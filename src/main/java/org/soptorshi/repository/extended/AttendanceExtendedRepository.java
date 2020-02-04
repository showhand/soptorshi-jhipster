package org.soptorshi.repository.extended;

import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.repository.AttendanceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceExtendedRepository extends AttendanceRepository {

    List<Attendance> getByAttendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload);
}
