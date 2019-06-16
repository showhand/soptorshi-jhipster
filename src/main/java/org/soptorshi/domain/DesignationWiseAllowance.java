package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.AllowanceType;

import org.soptorshi.domain.enumeration.AllowanceCategory;

/**
 * A DesignationWiseAllowance.
 */
@Entity
@Table(name = "designation_wise_allowance")
@Document(indexName = "designationwiseallowance")
public class DesignationWiseAllowance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "allowance_type")
    private AllowanceType allowanceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "allowance_category")
    private AllowanceCategory allowanceCategory;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("designationWiseAllowances")
    private Designation designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AllowanceType getAllowanceType() {
        return allowanceType;
    }

    public DesignationWiseAllowance allowanceType(AllowanceType allowanceType) {
        this.allowanceType = allowanceType;
        return this;
    }

    public void setAllowanceType(AllowanceType allowanceType) {
        this.allowanceType = allowanceType;
    }

    public AllowanceCategory getAllowanceCategory() {
        return allowanceCategory;
    }

    public DesignationWiseAllowance allowanceCategory(AllowanceCategory allowanceCategory) {
        this.allowanceCategory = allowanceCategory;
        return this;
    }

    public void setAllowanceCategory(AllowanceCategory allowanceCategory) {
        this.allowanceCategory = allowanceCategory;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DesignationWiseAllowance amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DesignationWiseAllowance modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public DesignationWiseAllowance modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Designation getDesignation() {
        return designation;
    }

    public DesignationWiseAllowance designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
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
        DesignationWiseAllowance designationWiseAllowance = (DesignationWiseAllowance) o;
        if (designationWiseAllowance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationWiseAllowance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationWiseAllowance{" +
            "id=" + getId() +
            ", allowanceType='" + getAllowanceType() + "'" +
            ", allowanceCategory='" + getAllowanceCategory() + "'" +
            ", amount=" + getAmount() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
