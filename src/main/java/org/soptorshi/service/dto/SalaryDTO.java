package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Salary entity.
 */
public class SalaryDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal basic;

    @NotNull
    private Double houseRent;

    @NotNull
    private Double medicalAllowance;

    private Double incrementRate;

    private Double otherAllowance;

    private Long modifiedBy;

    private LocalDate modifiedOn;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public Double getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(Double houseRent) {
        this.houseRent = houseRent;
    }

    public Double getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(Double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public Double getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(Double incrementRate) {
        this.incrementRate = incrementRate;
    }

    public Double getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(Double otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalaryDTO salaryDTO = (SalaryDTO) o;
        if (salaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalaryDTO{" +
            "id=" + getId() +
            ", basic=" + getBasic() +
            ", houseRent=" + getHouseRent() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", incrementRate=" + getIncrementRate() +
            ", otherAllowance=" + getOtherAllowance() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
