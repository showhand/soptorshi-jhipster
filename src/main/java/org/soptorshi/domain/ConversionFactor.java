package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ConversionFactor.
 */
@Entity
@Table(name = "conversion_factor")
@Document(indexName = "conversionfactor")
public class ConversionFactor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cov_factor", precision = 10, scale = 2)
    private BigDecimal covFactor;

    @Column(name = "rcov_factor", precision = 10, scale = 2)
    private BigDecimal rcovFactor;

    @Column(name = "bcov_factor", precision = 10, scale = 2)
    private BigDecimal bcovFactor;

    @Column(name = "rbcov_factor", precision = 10, scale = 2)
    private BigDecimal rbcovFactor;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("conversionFactors")
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCovFactor() {
        return covFactor;
    }

    public ConversionFactor covFactor(BigDecimal covFactor) {
        this.covFactor = covFactor;
        return this;
    }

    public void setCovFactor(BigDecimal covFactor) {
        this.covFactor = covFactor;
    }

    public BigDecimal getRcovFactor() {
        return rcovFactor;
    }

    public ConversionFactor rcovFactor(BigDecimal rcovFactor) {
        this.rcovFactor = rcovFactor;
        return this;
    }

    public void setRcovFactor(BigDecimal rcovFactor) {
        this.rcovFactor = rcovFactor;
    }

    public BigDecimal getBcovFactor() {
        return bcovFactor;
    }

    public ConversionFactor bcovFactor(BigDecimal bcovFactor) {
        this.bcovFactor = bcovFactor;
        return this;
    }

    public void setBcovFactor(BigDecimal bcovFactor) {
        this.bcovFactor = bcovFactor;
    }

    public BigDecimal getRbcovFactor() {
        return rbcovFactor;
    }

    public ConversionFactor rbcovFactor(BigDecimal rbcovFactor) {
        this.rbcovFactor = rbcovFactor;
        return this;
    }

    public void setRbcovFactor(BigDecimal rbcovFactor) {
        this.rbcovFactor = rbcovFactor;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ConversionFactor modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public ConversionFactor modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Currency getCurrency() {
        return currency;
    }

    public ConversionFactor currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        ConversionFactor conversionFactor = (ConversionFactor) o;
        if (conversionFactor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversionFactor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConversionFactor{" +
            "id=" + getId() +
            ", covFactor=" + getCovFactor() +
            ", rcovFactor=" + getRcovFactor() +
            ", bcovFactor=" + getBcovFactor() +
            ", rbcovFactor=" + getRbcovFactor() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
