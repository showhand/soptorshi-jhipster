package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.Attendance;
import org.soptorshi.service.dto.AttendanceDTO;

/**
 * Mapper for the entity Attendance and its DTO AttendanceDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, AttendanceExcelUploadMapper.class})
public interface AttendanceMapper extends EntityMapper<AttendanceDTO, Attendance> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    @Mapping(source = "attendanceExcelUpload.id", target = "attendanceExcelUploadId")
    AttendanceDTO toDto(Attendance attendance);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "attendanceExcelUploadId", target = "attendanceExcelUpload")
    Attendance toEntity(AttendanceDTO attendanceDTO);

    default Attendance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.setId(id);
        return attendance;
    }
}
