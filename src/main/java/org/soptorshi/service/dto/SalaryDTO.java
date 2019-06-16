package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.SalaryStatus;

/**
 * A DTO for the Salary entity.
 */
public class SalaryDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal basic;

    private LocalDate startedOn;

    private LocalDate endedOn;

    private SalaryStatus salaryStatus;

    private String modifiedBy;

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

    public LocalDate getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDate getEndedOn() {
        return endedOn;
    }

    public void setEndedOn(LocalDate endedOn) {
        this.endedOn = endedOn;
    }

    public SalaryStatus getSalaryStatus() {
        return salaryStatus;
    }

    public void setSalaryStatus(SalaryStatus salaryStatus) {
        this.salaryStatus = salaryStatus;
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
            ", startedOn='" + getStartedOn() + "'" +
            ", endedOn='" + getEndedOn() + "'" +
            ", salaryStatus='" + getSalaryStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
