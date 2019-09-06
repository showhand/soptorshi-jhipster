package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.MonthType;

import org.soptorshi.domain.enumeration.PeriodCloseFlag;

/**
 * A PeriodClose.
 */
@Entity
@Table(name = "period_close")
@Document(indexName = "periodclose")
public class PeriodClose implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "month_type")
    private MonthType monthType;

    @Column(name = "close_year")
    private Integer closeYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag")
    private PeriodCloseFlag flag;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("periodCloses")
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

    public PeriodClose monthType(MonthType monthType) {
        this.monthType = monthType;
        return this;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public Integer getCloseYear() {
        return closeYear;
    }

    public PeriodClose closeYear(Integer closeYear) {
        this.closeYear = closeYear;
        return this;
    }

    public void setCloseYear(Integer closeYear) {
        this.closeYear = closeYear;
    }

    public PeriodCloseFlag getFlag() {
        return flag;
    }

    public PeriodClose flag(PeriodCloseFlag flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(PeriodCloseFlag flag) {
        this.flag = flag;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public PeriodClose modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public PeriodClose modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public FinancialAccountYear getFinancialAccountYear() {
        return financialAccountYear;
    }

    public PeriodClose financialAccountYear(FinancialAccountYear financialAccountYear) {
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
        PeriodClose periodClose = (PeriodClose) o;
        if (periodClose.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodClose.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodClose{" +
            "id=" + getId() +
            ", monthType='" + getMonthType() + "'" +
            ", closeYear=" + getCloseYear() +
            ", flag='" + getFlag() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
