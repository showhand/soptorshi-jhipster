package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.CommercialPaymentStatus;
import org.soptorshi.domain.enumeration.PaymentType;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @NotNull
    @Column(name = "total_amount_to_pay", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmountToPay;

    @NotNull
    @Column(name = "total_amount_paid", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmountPaid;

    @NotNull
    @Column(name = "remaining_amount_to_pay", precision = 10, scale = 2, nullable = false)
    private BigDecimal remainingAmountToPay;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private CommercialPaymentStatus paymentStatus;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private CommercialPi commercialPi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public CommercialPaymentInfo paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getTotalAmountToPay() {
        return totalAmountToPay;
    }

    public CommercialPaymentInfo totalAmountToPay(BigDecimal totalAmountToPay) {
        this.totalAmountToPay = totalAmountToPay;
        return this;
    }

    public void setTotalAmountToPay(BigDecimal totalAmountToPay) {
        this.totalAmountToPay = totalAmountToPay;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public CommercialPaymentInfo totalAmountPaid(BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
        return this;
    }

    public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    public BigDecimal getRemainingAmountToPay() {
        return remainingAmountToPay;
    }

    public CommercialPaymentInfo remainingAmountToPay(BigDecimal remainingAmountToPay) {
        this.remainingAmountToPay = remainingAmountToPay;
        return this;
    }

    public void setRemainingAmountToPay(BigDecimal remainingAmountToPay) {
        this.remainingAmountToPay = remainingAmountToPay;
    }

    public CommercialPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public CommercialPaymentInfo paymentStatus(CommercialPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(CommercialPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CommercialPaymentInfo createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
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

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPaymentInfo updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
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

    public CommercialPi getCommercialPi() {
        return commercialPi;
    }

    public CommercialPaymentInfo commercialPi(CommercialPi commercialPi) {
        this.commercialPi = commercialPi;
        return this;
    }

    public void setCommercialPi(CommercialPi commercialPi) {
        this.commercialPi = commercialPi;
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
            ", paymentType='" + getPaymentType() + "'" +
            ", totalAmountToPay=" + getTotalAmountToPay() +
            ", totalAmountPaid=" + getTotalAmountPaid() +
            ", remainingAmountToPay=" + getRemainingAmountToPay() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
