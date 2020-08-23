package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import org.soptorshi.domain.enumeration.MonthType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A DepreciationCalculation.
 */
@Entity
@Table(name = "depreciation_calculation")
@Document(indexName = "depreciationcalculation")
@EntityListeners(AuditingEntityListener.class)
public class DepreciationCalculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "month_type")
    private MonthType monthType;

    @Column(name = "is_executed")
    private Boolean isExecuted;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_on")
    @CreatedDate
    private Instant createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Instant modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("depreciationCalculations")
    private FinancialAccountYear financialAccountYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public DepreciationCalculation monthType(MonthType monthType) {
        this.monthType = monthType;
        return this;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public Boolean isIsExecuted() {
        return isExecuted;
    }

    public DepreciationCalculation isExecuted(Boolean isExecuted) {
        this.isExecuted = isExecuted;
        return this;
    }

    public void setIsExecuted(Boolean isExecuted) {
        this.isExecuted = isExecuted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DepreciationCalculation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public DepreciationCalculation createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DepreciationCalculation modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedOn() {
        return modifiedOn;
    }

    public DepreciationCalculation modifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public FinancialAccountYear getFinancialAccountYear() {
        return financialAccountYear;
    }

    public DepreciationCalculation financialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
        return this;
    }

    public void setFinancialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
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
        DepreciationCalculation depreciationCalculation = (DepreciationCalculation) o;
        if (depreciationCalculation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depreciationCalculation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepreciationCalculation{" +
            "id=" + getId() +
            ", monthType='" + getMonthType() + "'" +
            ", isExecuted='" + isIsExecuted() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
