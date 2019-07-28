package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.AttendanceType;

/**
 * A DTO for the AttendanceExcelUpload entity.
 */
public class AttendanceExcelUploadDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] file;

    private String fileContentType;
    private AttendanceType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public AttendanceType getType() {
        return type;
    }

    public void setType(AttendanceType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendanceExcelUploadDTO attendanceExcelUploadDTO = (AttendanceExcelUploadDTO) o;
        if (attendanceExcelUploadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendanceExcelUploadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendanceExcelUploadDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
