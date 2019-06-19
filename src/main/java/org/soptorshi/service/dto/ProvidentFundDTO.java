package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ProvidentFundStatus;

/**
 * A DTO for the ProvidentFund entity.
 */
public class ProvidentFundDTO implements Serializable {

    private Long id;

    
    private LocalDate startDate;

    
    private Double rate;

    
    private ProvidentFundStatus status;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public ProvidentFundStatus getStatus() {
        return status;
    }

    public void setStatus(ProvidentFundStatus status) {
        this.status = status;
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

        ProvidentFundDTO providentFundDTO = (ProvidentFundDTO) o;
        if (providentFundDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providentFundDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProvidentFundDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", rate=" + getRate() +
            ", status='" + getStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullName() + "'" +
            "}";
    }
}
