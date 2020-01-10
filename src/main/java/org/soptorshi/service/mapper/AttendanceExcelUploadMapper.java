package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;

/**
 * Mapper for the entity AttendanceExcelUpload and its DTO AttendanceExcelUploadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttendanceExcelUploadMapper extends EntityMapper<AttendanceExcelUploadDTO, AttendanceExcelUpload> {


    @Mapping(target = "attendances", ignore = true)
    AttendanceExcelUpload toEntity(AttendanceExcelUploadDTO attendanceExcelUploadDTO);

    default AttendanceExcelUpload fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttendanceExcelUpload attendanceExcelUpload = new AttendanceExcelUpload();
        attendanceExcelUpload.setId(id);
        return attendanceExcelUpload;
    }
}
