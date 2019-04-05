package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ReferenceInformation entity.
 */
public class ReferenceInformationDTO implements Serializable {

    private Long id;

    private String name;

    private String designation;

    private String organization;

    private String contactNumber;


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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

        ReferenceInformationDTO referenceInformationDTO = (ReferenceInformationDTO) o;
        if (referenceInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referenceInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferenceInformationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
