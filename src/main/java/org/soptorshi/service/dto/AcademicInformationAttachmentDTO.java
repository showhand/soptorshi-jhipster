package org.soptorshi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the AcademicInformationAttachment entity.
 */
public class AcademicInformationAttachmentDTO implements Serializable {

    private Long id;

    
    @Lob
    private byte[] file;

    private String fileContentType;

    private Long employeeId;

    private String employeeEmployeeId;

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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmployeeId() {
        return employeeEmployeeId;
    }

    public void setEmployeeEmployeeId(String employeeEmployeeId) {
        this.employeeEmployeeId = employeeEmployeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AcademicInformationAttachmentDTO academicInformationAttachmentDTO = (AcademicInformationAttachmentDTO) o;
        if (academicInformationAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), academicInformationAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AcademicInformationAttachmentDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeEmployeeId() + "'" +
            "}";
    }
}
