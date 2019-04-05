package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FamilyInformation entity.
 */
public class FamilyInformationDTO implements Serializable {

    private Long id;

    private String name;

    private String relation;

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

        FamilyInformationDTO familyInformationDTO = (FamilyInformationDTO) o;
        if (familyInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familyInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FamilyInformationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", relation='" + getRelation() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
