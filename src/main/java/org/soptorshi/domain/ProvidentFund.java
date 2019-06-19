package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.ProvidentFundStatus;

/**
 * A ProvidentFund.
 */
@Entity
@Table(name = "provident_fund")
@Document(indexName = "providentfund")
public class ProvidentFund implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "start_date", unique = true)
    private LocalDate startDate;

    
    @Column(name = "rate", unique = true)
    private Double rate;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", unique = true)
    private ProvidentFundStatus status;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("providentFunds")
    private Employee employee;

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

    public ProvidentFund startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getRate() {
        return rate;
    }

    public ProvidentFund rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public ProvidentFundStatus getStatus() {
        return status;
    }

    public ProvidentFund status(ProvidentFundStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProvidentFundStatus status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ProvidentFund modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public ProvidentFund modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ProvidentFund employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        ProvidentFund providentFund = (ProvidentFund) o;
        if (providentFund.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providentFund.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProvidentFund{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", rate=" + getRate() +
            ", status='" + getStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
