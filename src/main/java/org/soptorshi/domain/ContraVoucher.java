package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.ApplicationType;

/**
 * A ContraVoucher.
 */
@Entity
@Table(name = "contra_voucher")
@Document(indexName = "contravoucher")
public class ContraVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_no")
    private String voucherNo;

    @Column(name = "voucher_date")
    private LocalDate voucherDate;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_type")
    private ApplicationType applicationType;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "conversion_factor", precision = 10, scale = 2)
    private BigDecimal conversionFactor;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("contraVouchers")
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public ContraVoucher voucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public ContraVoucher voucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
        return this;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public ContraVoucher postDate(LocalDate postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public ContraVoucher applicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public ContraVoucher applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public ContraVoucher conversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
        return this;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ContraVoucher modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public ContraVoucher modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Currency getCurrency() {
        return currency;
    }

    public ContraVoucher currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        ContraVoucher contraVoucher = (ContraVoucher) o;
        if (contraVoucher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contraVoucher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContraVoucher{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", applicationType='" + getApplicationType() + "'" +
            ", applicationId=" + getApplicationId() +
            ", conversionFactor=" + getConversionFactor() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
