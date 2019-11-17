package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.SalaryStatus;

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
    @Column(name = "gross", precision = 10, scale = 2, nullable = false)
    private BigDecimal gross;

    @Column(name = "started_on")
    private LocalDate startedOn;

    @Column(name = "ended_on")
    private LocalDate endedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_status")
    private SalaryStatus salaryStatus;

    @Column(name = "modified_by")
    private String modifiedBy;

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

    public BigDecimal getGross() {
        return gross;
    }

    public Salary gross(BigDecimal gross) {
        this.gross = gross;
        return this;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public LocalDate getStartedOn() {
        return startedOn;
    }

    public Salary startedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
        return this;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDate getEndedOn() {
        return endedOn;
    }

    public Salary endedOn(LocalDate endedOn) {
        this.endedOn = endedOn;
        return this;
    }

    public void setEndedOn(LocalDate endedOn) {
        this.endedOn = endedOn;
    }

    public SalaryStatus getSalaryStatus() {
        return salaryStatus;
    }

    public Salary salaryStatus(SalaryStatus salaryStatus) {
        this.salaryStatus = salaryStatus;
        return this;
    }

    public void setSalaryStatus(SalaryStatus salaryStatus) {
        this.salaryStatus = salaryStatus;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Salary modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
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
            ", gross=" + getGross() +
            ", startedOn='" + getStartedOn() + "'" +
            ", endedOn='" + getEndedOn() + "'" +
            ", salaryStatus='" + getSalaryStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
