package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.FinancialYearStatus;

/**
 * A DTO for the FinancialAccountYear entity.
 */
public class FinancialAccountYearDTO implements Serializable {

    private Long id;

    
    private LocalDate startDate;

    
    private LocalDate endDate;

    private LocalDate previousStartDate;

    private LocalDate previousEndDate;

    private String durationStr;

    
    private FinancialYearStatus status;


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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getPreviousStartDate() {
        return previousStartDate;
    }

    public void setPreviousStartDate(LocalDate previousStartDate) {
        this.previousStartDate = previousStartDate;
    }

    public LocalDate getPreviousEndDate() {
        return previousEndDate;
    }

    public void setPreviousEndDate(LocalDate previousEndDate) {
        this.previousEndDate = previousEndDate;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }

    public FinancialYearStatus getStatus() {
        return status;
    }

    public void setStatus(FinancialYearStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FinancialAccountYearDTO financialAccountYearDTO = (FinancialAccountYearDTO) o;
        if (financialAccountYearDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financialAccountYearDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinancialAccountYearDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", previousStartDate='" + getPreviousStartDate() + "'" +
            ", previousEndDate='" + getPreviousEndDate() + "'" +
            ", durationStr='" + getDurationStr() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
