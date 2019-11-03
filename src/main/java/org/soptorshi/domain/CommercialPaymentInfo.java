package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.CommercialCurrency;
import org.soptorshi.domain.enumeration.CommercialPaymentCategory;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialPaymentInfo.
 */
@Entity
@Table(name = "commercial_payment_info")
@Document(indexName = "commercialpaymentinfo")
public class CommercialPaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_category", nullable = false)
    private CommercialPaymentCategory paymentCategory;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CommercialCurrency currencyType;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_on")
    private LocalDate createOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;

    @OneToOne
    @JoinColumn(unique = true)
    private CommercialPurchaseOrder commercialPurchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommercialPaymentCategory getPaymentCategory() {
        return paymentCategory;
    }

    public CommercialPaymentInfo paymentCategory(CommercialPaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
        return this;
    }

    public void setPaymentCategory(CommercialPaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public CommercialPaymentInfo totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public CommercialCurrency getCurrencyType() {
        return currencyType;
    }

    public CommercialPaymentInfo currencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
        return this;
    }

    public void setCurrencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public CommercialPaymentInfo paymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
        return this;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPaymentInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public CommercialPaymentInfo createOn(LocalDate createOn) {
        this.createOn = createOn;
        return this;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPaymentInfo updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPaymentInfo updatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPurchaseOrder getCommercialPurchaseOrder() {
        return commercialPurchaseOrder;
    }

    public CommercialPaymentInfo commercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
        return this;
    }

    public void setCommercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
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
        CommercialPaymentInfo commercialPaymentInfo = (CommercialPaymentInfo) o;
        if (commercialPaymentInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPaymentInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPaymentInfo{" +
            "id=" + getId() +
            ", paymentCategory='" + getPaymentCategory() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", currencyType='" + getCurrencyType() + "'" +
            ", paymentTerms='" + getPaymentTerms() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
