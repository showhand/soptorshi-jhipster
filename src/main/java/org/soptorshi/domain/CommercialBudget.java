package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.domain.enumeration.CommercialCustomerCategory;
import org.soptorshi.domain.enumeration.CommercialOrderCategory;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialBudget.
 */
@Entity
@Table(name = "commercial_budget")
@Document(indexName = "commercialbudget")
public class CommercialBudget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "budget_no", nullable = false)
    private String budgetNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private CommercialOrderCategory type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "customer", nullable = false)
    private CommercialCustomerCategory customer;

    @NotNull
    @Column(name = "budget_date", nullable = false)
    private LocalDate budgetDate;

    @Column(name = "offered_price", precision = 10, scale = 2)
    private BigDecimal offeredPrice;

    @Column(name = "buying_price", precision = 10, scale = 2)
    private BigDecimal buyingPrice;

    @Column(name = "profit_amount", precision = 10, scale = 2)
    private BigDecimal profitAmount;

    @Column(name = "profit_percentage", precision = 10, scale = 2)
    private BigDecimal profitPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_status")
    private CommercialBudgetStatus budgetStatus;

    @Column(name = "proforma_no")
    private String proformaNo;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBudgetNo() {
        return budgetNo;
    }

    public CommercialBudget budgetNo(String budgetNo) {
        this.budgetNo = budgetNo;
        return this;
    }

    public void setBudgetNo(String budgetNo) {
        this.budgetNo = budgetNo;
    }

    public CommercialOrderCategory getType() {
        return type;
    }

    public CommercialBudget type(CommercialOrderCategory type) {
        this.type = type;
        return this;
    }

    public void setType(CommercialOrderCategory type) {
        this.type = type;
    }

    public CommercialCustomerCategory getCustomer() {
        return customer;
    }

    public CommercialBudget customer(CommercialCustomerCategory customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(CommercialCustomerCategory customer) {
        this.customer = customer;
    }

    public LocalDate getBudgetDate() {
        return budgetDate;
    }

    public CommercialBudget budgetDate(LocalDate budgetDate) {
        this.budgetDate = budgetDate;
        return this;
    }

    public void setBudgetDate(LocalDate budgetDate) {
        this.budgetDate = budgetDate;
    }

    public BigDecimal getOfferedPrice() {
        return offeredPrice;
    }

    public CommercialBudget offeredPrice(BigDecimal offeredPrice) {
        this.offeredPrice = offeredPrice;
        return this;
    }

    public void setOfferedPrice(BigDecimal offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public CommercialBudget buyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
        return this;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public CommercialBudget profitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
        return this;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }

    public BigDecimal getProfitPercentage() {
        return profitPercentage;
    }

    public CommercialBudget profitPercentage(BigDecimal profitPercentage) {
        this.profitPercentage = profitPercentage;
        return this;
    }

    public void setProfitPercentage(BigDecimal profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public CommercialBudgetStatus getBudgetStatus() {
        return budgetStatus;
    }

    public CommercialBudget budgetStatus(CommercialBudgetStatus budgetStatus) {
        this.budgetStatus = budgetStatus;
        return this;
    }

    public void setBudgetStatus(CommercialBudgetStatus budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public String getProformaNo() {
        return proformaNo;
    }

    public CommercialBudget proformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
        return this;
    }

    public void setProformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialBudget createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CommercialBudget createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialBudget updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public CommercialBudget updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
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
        CommercialBudget commercialBudget = (CommercialBudget) o;
        if (commercialBudget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialBudget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialBudget{" +
            "id=" + getId() +
            ", budgetNo='" + getBudgetNo() + "'" +
            ", type='" + getType() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", budgetDate='" + getBudgetDate() + "'" +
            ", offeredPrice=" + getOfferedPrice() +
            ", buyingPrice=" + getBuyingPrice() +
            ", profitAmount=" + getProfitAmount() +
            ", profitPercentage=" + getProfitPercentage() +
            ", budgetStatus='" + getBudgetStatus() + "'" +
            ", proformaNo='" + getProformaNo() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
