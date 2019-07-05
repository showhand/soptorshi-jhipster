package org.soptorshi.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.FinancialYearStatus;

/**
 * A FinancialAccountYear.
 */
@Entity
@Table(name = "financial_account_year")
@Document(indexName = "financialaccountyear")
public class FinancialAccountYear implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "start_date", unique = true)
    private LocalDate startDate;

    
    @Column(name = "end_date", unique = true)
    private LocalDate endDate;

    @Column(name = "previous_start_date")
    private LocalDate previousStartDate;

    @Column(name = "previous_end_date")
    private LocalDate previousEndDate;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", unique = true)
    private FinancialYearStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public FinancialAccountYear startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public FinancialAccountYear endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getPreviousStartDate() {
        return previousStartDate;
    }

    public FinancialAccountYear previousStartDate(LocalDate previousStartDate) {
        this.previousStartDate = previousStartDate;
        return this;
    }

    public void setPreviousStartDate(LocalDate previousStartDate) {
        this.previousStartDate = previousStartDate;
    }

    public LocalDate getPreviousEndDate() {
        return previousEndDate;
    }

    public FinancialAccountYear previousEndDate(LocalDate previousEndDate) {
        this.previousEndDate = previousEndDate;
        return this;
    }

    public void setPreviousEndDate(LocalDate previousEndDate) {
        this.previousEndDate = previousEndDate;
    }

    public FinancialYearStatus getStatus() {
        return status;
    }

    public FinancialAccountYear status(FinancialYearStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FinancialYearStatus status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FinancialAccountYear financialAccountYear = (FinancialAccountYear) o;
        if (financialAccountYear.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financialAccountYear.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinancialAccountYear{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", previousStartDate='" + getPreviousStartDate() + "'" +
            ", previousEndDate='" + getPreviousEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
