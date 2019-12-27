package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.domain.enumeration.CommercialCustomerCategory;
import org.soptorshi.domain.enumeration.CommercialOrderCategory;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialBudget entity. This class is used in CommercialBudgetResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-budgets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialBudgetCriteria implements Serializable {
    /**
     * Class for filtering CommercialOrderCategory
     */
    public static class CommercialOrderCategoryFilter extends Filter<CommercialOrderCategory> {
    }
    /**
     * Class for filtering CommercialCustomerCategory
     */
    public static class CommercialCustomerCategoryFilter extends Filter<CommercialCustomerCategory> {
    }
    /**
     * Class for filtering CommercialBudgetStatus
     */
    public static class CommercialBudgetStatusFilter extends Filter<CommercialBudgetStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter budgetNo;

    private CommercialOrderCategoryFilter type;

    private CommercialCustomerCategoryFilter customer;

    private LocalDateFilter budgetDate;

    private BigDecimalFilter offeredPrice;

    private BigDecimalFilter buyingPrice;

    private BigDecimalFilter profitAmount;

    private BigDecimalFilter profitPercentage;

    private CommercialBudgetStatusFilter budgetStatus;

    private StringFilter proformaNo;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBudgetNo() {
        return budgetNo;
    }

    public void setBudgetNo(StringFilter budgetNo) {
        this.budgetNo = budgetNo;
    }

    public CommercialOrderCategoryFilter getType() {
        return type;
    }

    public void setType(CommercialOrderCategoryFilter type) {
        this.type = type;
    }

    public CommercialCustomerCategoryFilter getCustomer() {
        return customer;
    }

    public void setCustomer(CommercialCustomerCategoryFilter customer) {
        this.customer = customer;
    }

    public LocalDateFilter getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(LocalDateFilter budgetDate) {
        this.budgetDate = budgetDate;
    }

    public BigDecimalFilter getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(BigDecimalFilter offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public BigDecimalFilter getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimalFilter buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimalFilter getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimalFilter profitAmount) {
        this.profitAmount = profitAmount;
    }

    public BigDecimalFilter getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(BigDecimalFilter profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public CommercialBudgetStatusFilter getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(CommercialBudgetStatusFilter budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public StringFilter getProformaNo() {
        return proformaNo;
    }

    public void setProformaNo(StringFilter proformaNo) {
        this.proformaNo = proformaNo;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
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
        final CommercialBudgetCriteria that = (CommercialBudgetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(budgetNo, that.budgetNo) &&
            Objects.equals(type, that.type) &&
            Objects.equals(customer, that.customer) &&
            Objects.equals(budgetDate, that.budgetDate) &&
            Objects.equals(offeredPrice, that.offeredPrice) &&
            Objects.equals(buyingPrice, that.buyingPrice) &&
            Objects.equals(profitAmount, that.profitAmount) &&
            Objects.equals(profitPercentage, that.profitPercentage) &&
            Objects.equals(budgetStatus, that.budgetStatus) &&
            Objects.equals(proformaNo, that.proformaNo) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        budgetNo,
        type,
        customer,
        budgetDate,
        offeredPrice,
        buyingPrice,
        profitAmount,
        profitPercentage,
        budgetStatus,
        proformaNo,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn
        );
    }

    @Override
    public String toString() {
        return "CommercialBudgetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (budgetNo != null ? "budgetNo=" + budgetNo + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (customer != null ? "customer=" + customer + ", " : "") +
                (budgetDate != null ? "budgetDate=" + budgetDate + ", " : "") +
                (offeredPrice != null ? "offeredPrice=" + offeredPrice + ", " : "") +
                (buyingPrice != null ? "buyingPrice=" + buyingPrice + ", " : "") +
                (profitAmount != null ? "profitAmount=" + profitAmount + ", " : "") +
                (profitPercentage != null ? "profitPercentage=" + profitPercentage + ", " : "") +
                (budgetStatus != null ? "budgetStatus=" + budgetStatus + ", " : "") +
                (proformaNo != null ? "proformaNo=" + proformaNo + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
            "}";
    }

}
