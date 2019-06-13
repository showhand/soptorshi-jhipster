package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Manager entity.
 */
public class ManagerDTO implements Serializable {

    private Long id;

    private Long parentEmployeeId;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long employeeId;

    private String employeeFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentEmployeeId() {
        return parentEmployeeId;
    }

    public void setParentEmployeeId(Long parentEmployeeId) {
        this.parentEmployeeId = parentEmployeeId;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManagerDTO managerDTO = (ManagerDTO) o;
        if (managerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), managerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ManagerDTO{" +
            "id=" + getId() +
            ", parentEmployeeId=" + getParentEmployeeId() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullName() + "'" +
            "}";
    }
}
