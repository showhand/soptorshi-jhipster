package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.soptorshi.domain.enumeration.TaxStatus;

/**
 * A Tax.
 */
@Entity
@Table(name = "tax")
@Document(indexName = "tax")
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "minimum_salary", precision = 10, scale = 2)
    private BigDecimal minimumSalary;

    @Column(name = "rate")
    private Double rate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_status")
    private TaxStatus taxStatus;

    @ManyToOne
    @JsonIgnoreProperties("taxes")
    private FinancialAccountYear financialAccountYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinimumSalary() {
        return minimumSalary;
    }

    public Tax minimumSalary(BigDecimal minimumSalary) {
        this.minimumSalary = minimumSalary;
        return this;
    }

    public void setMinimumSalary(BigDecimal minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public Double getRate() {
        return rate;
    }

    public Tax rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public TaxStatus getTaxStatus() {
        return taxStatus;
    }

    public Tax taxStatus(TaxStatus taxStatus) {
        this.taxStatus = taxStatus;
        return this;
    }

    public void setTaxStatus(TaxStatus taxStatus) {
        this.taxStatus = taxStatus;
    }

    public FinancialAccountYear getFinancialAccountYear() {
        return financialAccountYear;
    }

    public Tax financialAccountYear(FinancialAccountYear financialAccountYear) {
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
        Tax tax = (Tax) o;
        if (tax.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tax.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tax{" +
            "id=" + getId() +
            ", minimumSalary=" + getMinimumSalary() +
            ", rate=" + getRate() +
            ", taxStatus='" + getTaxStatus() + "'" +
            "}";
    }
}
