package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.*;

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

    private String companyName;

    private PaymentType paymentType;

    @NotNull
    private TransportType transportationType;

    private String seaPortName;

    private BigDecimal seaPortCost;

    private String airPortName;

    private BigDecimal airPortCost;

    private String landPortName;

    private BigDecimal landPortCost;

    private BigDecimal insurancePrice;

    private BigDecimal totalTransportationCost;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public TransportType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportType transportationType) {
        this.transportationType = transportationType;
    }

    public String getSeaPortName() {
        return seaPortName;
    }

    public void setSeaPortName(String seaPortName) {
        this.seaPortName = seaPortName;
    }

    public BigDecimal getSeaPortCost() {
        return seaPortCost;
    }

    public void setSeaPortCost(BigDecimal seaPortCost) {
        this.seaPortCost = seaPortCost;
    }

    public String getAirPortName() {
        return airPortName;
    }

    public void setAirPortName(String airPortName) {
        this.airPortName = airPortName;
    }

    public BigDecimal getAirPortCost() {
        return airPortCost;
    }

    public void setAirPortCost(BigDecimal airPortCost) {
        this.airPortCost = airPortCost;
    }

    public String getLandPortName() {
        return landPortName;
    }

    public void setLandPortName(String landPortName) {
        this.landPortName = landPortName;
    }

    public BigDecimal getLandPortCost() {
        return landPortCost;
    }

    public void setLandPortCost(BigDecimal landPortCost) {
        this.landPortCost = landPortCost;
    }

    public BigDecimal getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public BigDecimal getTotalTransportationCost() {
        return totalTransportationCost;
    }

    public void setTotalTransportationCost(BigDecimal totalTransportationCost) {
        this.totalTransportationCost = totalTransportationCost;
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
            ", companyName='" + getCompanyName() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", transportationType='" + getTransportationType() + "'" +
            ", seaPortName='" + getSeaPortName() + "'" +
            ", seaPortCost=" + getSeaPortCost() +
            ", airPortName='" + getAirPortName() + "'" +
            ", airPortCost=" + getAirPortCost() +
            ", landPortName='" + getLandPortName() + "'" +
            ", landPortCost=" + getLandPortCost() +
            ", insurancePrice=" + getInsurancePrice() +
            ", totalTransportationCost=" + getTotalTransportationCost() +
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
