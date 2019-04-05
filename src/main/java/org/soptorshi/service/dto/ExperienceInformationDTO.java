package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.EmploymentType;

/**
 * A DTO for the ExperienceInformation entity.
 */
public class ExperienceInformationDTO implements Serializable {

    private Long id;

    private String organization;

    private String designation;

    private LocalDate startDate;

    private LocalDate endDate;

    private EmploymentType employmentType;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
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

        ExperienceInformationDTO experienceInformationDTO = (ExperienceInformationDTO) o;
        if (experienceInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experienceInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperienceInformationDTO{" +
            "id=" + getId() +
            ", organization='" + getOrganization() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", employmentType='" + getEmploymentType() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
