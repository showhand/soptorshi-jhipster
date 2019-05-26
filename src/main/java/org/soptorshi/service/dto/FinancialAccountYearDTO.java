package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FinancialAccountYear entity.
 */
public class FinancialAccountYearDTO implements Serializable {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long previousYear;

    private Boolean status;


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

    public Long getPreviousYear() {
        return previousYear;
    }

    public void setPreviousYear(Long previousYear) {
        this.previousYear = previousYear;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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
            ", previousYear=" + getPreviousYear() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
