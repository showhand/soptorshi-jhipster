package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.*;
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

    @Column(name = "company_name")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transportation_type", nullable = false)
    private TransportType transportationType;

    @Column(name = "sea_port_name")
    private String seaPortName;

    @Column(name = "sea_port_cost", precision = 10, scale = 2)
    private BigDecimal seaPortCost;

    @Column(name = "air_port_name")
    private String airPortName;

    @Column(name = "air_port_cost", precision = 10, scale = 2)
    private BigDecimal airPortCost;

    @Column(name = "land_port_name")
    private String landPortName;

    @Column(name = "land_port_cost", precision = 10, scale = 2)
    private BigDecimal landPortCost;

    @Column(name = "insurance_price", precision = 10, scale = 2)
    private BigDecimal insurancePrice;

    @Column(name = "total_transportation_cost", precision = 10, scale = 2)
    private BigDecimal totalTransportationCost;

    @Column(name = "total_quantity", precision = 10, scale = 2)
    private BigDecimal totalQuantity;

    @Column(name = "total_offered_price", precision = 10, scale = 2)
    private BigDecimal totalOfferedPrice;

    @Column(name = "total_buying_price", precision = 10, scale = 2)
    private BigDecimal totalBuyingPrice;

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

    public String getCompanyName() {
        return companyName;
    }

    public CommercialBudget companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public CommercialBudget paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public TransportType getTransportationType() {
        return transportationType;
    }

    public CommercialBudget transportationType(TransportType transportationType) {
        this.transportationType = transportationType;
        return this;
    }

    public void setTransportationType(TransportType transportationType) {
        this.transportationType = transportationType;
    }

    public String getSeaPortName() {
        return seaPortName;
    }

    public CommercialBudget seaPortName(String seaPortName) {
        this.seaPortName = seaPortName;
        return this;
    }

    public void setSeaPortName(String seaPortName) {
        this.seaPortName = seaPortName;
    }

    public BigDecimal getSeaPortCost() {
        return seaPortCost;
    }

    public CommercialBudget seaPortCost(BigDecimal seaPortCost) {
        this.seaPortCost = seaPortCost;
        return this;
    }

    public void setSeaPortCost(BigDecimal seaPortCost) {
        this.seaPortCost = seaPortCost;
    }

    public String getAirPortName() {
        return airPortName;
    }

    public CommercialBudget airPortName(String airPortName) {
        this.airPortName = airPortName;
        return this;
    }

    public void setAirPortName(String airPortName) {
        this.airPortName = airPortName;
    }

    public BigDecimal getAirPortCost() {
        return airPortCost;
    }

    public CommercialBudget airPortCost(BigDecimal airPortCost) {
        this.airPortCost = airPortCost;
        return this;
    }

    public void setAirPortCost(BigDecimal airPortCost) {
        this.airPortCost = airPortCost;
    }

    public String getLandPortName() {
        return landPortName;
    }

    public CommercialBudget landPortName(String landPortName) {
        this.landPortName = landPortName;
        return this;
    }

    public void setLandPortName(String landPortName) {
        this.landPortName = landPortName;
    }

    public BigDecimal getLandPortCost() {
        return landPortCost;
    }

    public CommercialBudget landPortCost(BigDecimal landPortCost) {
        this.landPortCost = landPortCost;
        return this;
    }

    public void setLandPortCost(BigDecimal landPortCost) {
        this.landPortCost = landPortCost;
    }

    public BigDecimal getInsurancePrice() {
        return insurancePrice;
    }

    public CommercialBudget insurancePrice(BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
        return this;
    }

    public void setInsurancePrice(BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public BigDecimal getTotalTransportationCost() {
        return totalTransportationCost;
    }

    public CommercialBudget totalTransportationCost(BigDecimal totalTransportationCost) {
        this.totalTransportationCost = totalTransportationCost;
        return this;
    }

    public void setTotalTransportationCost(BigDecimal totalTransportationCost) {
        this.totalTransportationCost = totalTransportationCost;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public CommercialBudget totalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalOfferedPrice() {
        return totalOfferedPrice;
    }

    public CommercialBudget totalOfferedPrice(BigDecimal totalOfferedPrice) {
        this.totalOfferedPrice = totalOfferedPrice;
        return this;
    }

    public void setTotalOfferedPrice(BigDecimal totalOfferedPrice) {
        this.totalOfferedPrice = totalOfferedPrice;
    }

    public BigDecimal getTotalBuyingPrice() {
        return totalBuyingPrice;
    }

    public CommercialBudget totalBuyingPrice(BigDecimal totalBuyingPrice) {
        this.totalBuyingPrice = totalBuyingPrice;
        return this;
    }

    public void setTotalBuyingPrice(BigDecimal totalBuyingPrice) {
        this.totalBuyingPrice = totalBuyingPrice;
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
