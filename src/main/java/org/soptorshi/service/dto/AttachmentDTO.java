package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Attachment entity.
 */
public class AttachmentDTO implements Serializable {

    private Long id;

    private String fileName;

    private String path;


    private Long academicInformationId;

    private Long trainingInformationId;

    private Long experienceInformationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getAcademicInformationId() {
        return academicInformationId;
    }

    public void setAcademicInformationId(Long academicInformationId) {
        this.academicInformationId = academicInformationId;
    }

    public Long getTrainingInformationId() {
        return trainingInformationId;
    }

    public void setTrainingInformationId(Long trainingInformationId) {
        this.trainingInformationId = trainingInformationId;
    }

    public Long getExperienceInformationId() {
        return experienceInformationId;
    }

    public void setExperienceInformationId(Long experienceInformationId) {
        this.experienceInformationId = experienceInformationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttachmentDTO attachmentDTO = (AttachmentDTO) o;
        if (attachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", path='" + getPath() + "'" +
            ", academicInformation=" + getAcademicInformationId() +
            ", trainingInformation=" + getTrainingInformationId() +
            ", experienceInformation=" + getExperienceInformationId() +
            "}";
    }
}
