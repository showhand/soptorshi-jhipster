package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.*;

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
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {
    }
    /**
     * Class for filtering TransportType
     */
    public static class TransportTypeFilter extends Filter<TransportType> {
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

    private StringFilter companyName;

    private PaymentTypeFilter paymentType;

    private TransportTypeFilter transportationType;

    private StringFilter seaPortName;

    private BigDecimalFilter seaPortCost;

    private StringFilter airPortName;

    private BigDecimalFilter airPortCost;

    private StringFilter landPortName;

    private BigDecimalFilter landPortCost;

    private BigDecimalFilter insurancePrice;

    private BigDecimalFilter totalTransportationCost;

    private BigDecimalFilter totalQuantity;

    private BigDecimalFilter totalOfferedPrice;

    private BigDecimalFilter totalBuyingPrice;

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

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public PaymentTypeFilter getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeFilter paymentType) {
        this.paymentType = paymentType;
    }

    public TransportTypeFilter getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportTypeFilter transportationType) {
        this.transportationType = transportationType;
    }

    public StringFilter getSeaPortName() {
        return seaPortName;
    }

    public void setSeaPortName(StringFilter seaPortName) {
        this.seaPortName = seaPortName;
    }

    public BigDecimalFilter getSeaPortCost() {
        return seaPortCost;
    }

    public void setSeaPortCost(BigDecimalFilter seaPortCost) {
        this.seaPortCost = seaPortCost;
    }

    public StringFilter getAirPortName() {
        return airPortName;
    }

    public void setAirPortName(StringFilter airPortName) {
        this.airPortName = airPortName;
    }

    public BigDecimalFilter getAirPortCost() {
        return airPortCost;
    }

    public void setAirPortCost(BigDecimalFilter airPortCost) {
        this.airPortCost = airPortCost;
    }

    public StringFilter getLandPortName() {
        return landPortName;
    }

    public void setLandPortName(StringFilter landPortName) {
        this.landPortName = landPortName;
    }

    public BigDecimalFilter getLandPortCost() {
        return landPortCost;
    }

    public void setLandPortCost(BigDecimalFilter landPortCost) {
        this.landPortCost = landPortCost;
    }

    public BigDecimalFilter getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(BigDecimalFilter insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public BigDecimalFilter getTotalTransportationCost() {
        return totalTransportationCost;
    }

    public void setTotalTransportationCost(BigDecimalFilter totalTransportationCost) {
        this.totalTransportationCost = totalTransportationCost;
    }

    public BigDecimalFilter getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimalFilter totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimalFilter getTotalOfferedPrice() {
        return totalOfferedPrice;
    }

    public void setTotalOfferedPrice(BigDecimalFilter totalOfferedPrice) {
        this.totalOfferedPrice = totalOfferedPrice;
    }

    public BigDecimalFilter getTotalBuyingPrice() {
        return totalBuyingPrice;
    }

    public void setTotalBuyingPrice(BigDecimalFilter totalBuyingPrice) {
        this.totalBuyingPrice = totalBuyingPrice;
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
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(transportationType, that.transportationType) &&
            Objects.equals(seaPortName, that.seaPortName) &&
            Objects.equals(seaPortCost, that.seaPortCost) &&
            Objects.equals(airPortName, that.airPortName) &&
            Objects.equals(airPortCost, that.airPortCost) &&
            Objects.equals(landPortName, that.landPortName) &&
            Objects.equals(landPortCost, that.landPortCost) &&
            Objects.equals(insurancePrice, that.insurancePrice) &&
            Objects.equals(totalTransportationCost, that.totalTransportationCost) &&
            Objects.equals(totalQuantity, that.totalQuantity) &&
            Objects.equals(totalOfferedPrice, that.totalOfferedPrice) &&
            Objects.equals(totalBuyingPrice, that.totalBuyingPrice) &&
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
        companyName,
        paymentType,
        transportationType,
        seaPortName,
        seaPortCost,
        airPortName,
        airPortCost,
        landPortName,
        landPortCost,
        insurancePrice,
        totalTransportationCost,
        totalQuantity,
        totalOfferedPrice,
        totalBuyingPrice,
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
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
                (transportationType != null ? "transportationType=" + transportationType + ", " : "") +
                (seaPortName != null ? "seaPortName=" + seaPortName + ", " : "") +
                (seaPortCost != null ? "seaPortCost=" + seaPortCost + ", " : "") +
                (airPortName != null ? "airPortName=" + airPortName + ", " : "") +
                (airPortCost != null ? "airPortCost=" + airPortCost + ", " : "") +
                (landPortName != null ? "landPortName=" + landPortName + ", " : "") +
                (landPortCost != null ? "landPortCost=" + landPortCost + ", " : "") +
                (insurancePrice != null ? "insurancePrice=" + insurancePrice + ", " : "") +
                (totalTransportationCost != null ? "totalTransportationCost=" + totalTransportationCost + ", " : "") +
                (totalQuantity != null ? "totalQuantity=" + totalQuantity + ", " : "") +
                (totalOfferedPrice != null ? "totalOfferedPrice=" + totalOfferedPrice + ", " : "") +
                (totalBuyingPrice != null ? "totalBuyingPrice=" + totalBuyingPrice + ", " : "") +
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
