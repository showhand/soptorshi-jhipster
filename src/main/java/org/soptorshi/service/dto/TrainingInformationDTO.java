package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TrainingInformation entity.
 */
public class TrainingInformationDTO implements Serializable {

    private Long id;

    private String name;

    private String subject;

    private String organization;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainingInformationDTO trainingInformationDTO = (TrainingInformationDTO) o;
        if (trainingInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainingInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainingInformationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subject='" + getSubject() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
