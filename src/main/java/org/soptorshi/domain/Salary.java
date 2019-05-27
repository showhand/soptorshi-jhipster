package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Salary.
 */
@Entity
@Table(name = "salary")
@Document(indexName = "salary")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "basic", precision = 10, scale = 2, nullable = false)
    private BigDecimal basic;

    @NotNull
    @Column(name = "house_rent", nullable = false)
    private Double houseRent;

    @NotNull
    @Column(name = "medical_allowance", nullable = false)
    private Double medicalAllowance;

    @Column(name = "increment_rate")
    private Double incrementRate;

    @Column(name = "other_allowance")
    private Double otherAllowance;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("salaries")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public Salary basic(BigDecimal basic) {
        this.basic = basic;
        return this;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public Double getHouseRent() {
        return houseRent;
    }

    public Salary houseRent(Double houseRent) {
        this.houseRent = houseRent;
        return this;
    }

    public void setHouseRent(Double houseRent) {
        this.houseRent = houseRent;
    }

    public Double getMedicalAllowance() {
        return medicalAllowance;
    }

    public Salary medicalAllowance(Double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
        return this;
    }

    public void setMedicalAllowance(Double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public Double getIncrementRate() {
        return incrementRate;
    }

    public Salary incrementRate(Double incrementRate) {
        this.incrementRate = incrementRate;
        return this;
    }

    public void setIncrementRate(Double incrementRate) {
        this.incrementRate = incrementRate;
    }

    public Double getOtherAllowance() {
        return otherAllowance;
    }

    public Salary otherAllowance(Double otherAllowance) {
        this.otherAllowance = otherAllowance;
        return this;
    }

    public void setOtherAllowance(Double otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public Salary modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public Salary modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Salary employee(Employee employee) {
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
        Salary salary = (Salary) o;
        if (salary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Salary{" +
            "id=" + getId() +
            ", basic=" + getBasic() +
            ", houseRent=" + getHouseRent() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", incrementRate=" + getIncrementRate() +
            ", otherAllowance=" + getOtherAllowance() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
