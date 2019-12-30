package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.domain.enumeration.CommercialCustomerCategory;
import org.soptorshi.domain.enumeration.CommercialOrderCategory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialBudget entity.
 */
public class CommercialBudgetDTO implements Serializable {

    private Long id;

    @NotNull
    private String budgetNo;

    @NotNull
    private CommercialOrderCategory type;

    @NotNull
    private CommercialCustomerCategory customer;

    @NotNull
    private LocalDate budgetDate;

    private BigDecimal totalQuantity;

    private BigDecimal totalOfferedPrice;

    private BigDecimal totalBuyingPrice;

    private BigDecimal profitAmount;

    private BigDecimal profitPercentage;

    private CommercialBudgetStatus budgetStatus;

    private String proformaNo;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBudgetNo() {
        return budgetNo;
    }

    public void setBudgetNo(String budgetNo) {
        this.budgetNo = budgetNo;
    }

    public CommercialOrderCategory getType() {
        return type;
    }

    public void setType(CommercialOrderCategory type) {
        this.type = type;
    }

    public CommercialCustomerCategory getCustomer() {
        return customer;
    }

    public void setCustomer(CommercialCustomerCategory customer) {
        this.customer = customer;
    }

    public LocalDate getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(LocalDate budgetDate) {
        this.budgetDate = budgetDate;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalOfferedPrice() {
        return totalOfferedPrice;
    }

    public void setTotalOfferedPrice(BigDecimal totalOfferedPrice) {
        this.totalOfferedPrice = totalOfferedPrice;
    }

    public BigDecimal getTotalBuyingPrice() {
        return totalBuyingPrice;
    }

    public void setTotalBuyingPrice(BigDecimal totalBuyingPrice) {
        this.totalBuyingPrice = totalBuyingPrice;
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }

    public BigDecimal getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(BigDecimal profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public CommercialBudgetStatus getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(CommercialBudgetStatus budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public String getProformaNo() {
        return proformaNo;
    }

    public void setProformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialBudgetDTO commercialBudgetDTO = (CommercialBudgetDTO) o;
        if (commercialBudgetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialBudgetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialBudgetDTO{" +
            "id=" + getId() +
            ", budgetNo='" + getBudgetNo() + "'" +
            ", type='" + getType() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", budgetDate='" + getBudgetDate() + "'" +
            ", totalQuantity=" + getTotalQuantity() +
            ", totalOfferedPrice=" + getTotalOfferedPrice() +
            ", totalBuyingPrice=" + getTotalBuyingPrice() +
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
