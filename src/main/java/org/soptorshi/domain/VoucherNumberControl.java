package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.VoucherResetBasis;

/**
 * A VoucherNumberControl.
 */
@Entity
@Table(name = "voucher_number_control")
@Document(indexName = "vouchernumbercontrol")
public class VoucherNumberControl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reset_basis")
    private VoucherResetBasis resetBasis;

    @Column(name = "start_voucher_no")
    private Integer startVoucherNo;

    @Column(name = "voucher_limit", precision = 10, scale = 2)
    private BigDecimal voucherLimit;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties("voucherNumberControls")
    private FinancialAccountYear financialAccountYear;

    @ManyToOne
    @JsonIgnoreProperties("voucherNumberControls")
    private Voucher voucher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoucherResetBasis getResetBasis() {
        return resetBasis;
    }

    public VoucherNumberControl resetBasis(VoucherResetBasis resetBasis) {
        this.resetBasis = resetBasis;
        return this;
    }

    public void setResetBasis(VoucherResetBasis resetBasis) {
        this.resetBasis = resetBasis;
    }

    public Integer getStartVoucherNo() {
        return startVoucherNo;
    }

    public VoucherNumberControl startVoucherNo(Integer startVoucherNo) {
        this.startVoucherNo = startVoucherNo;
        return this;
    }

    public void setStartVoucherNo(Integer startVoucherNo) {
        this.startVoucherNo = startVoucherNo;
    }

    public BigDecimal getVoucherLimit() {
        return voucherLimit;
    }

    public VoucherNumberControl voucherLimit(BigDecimal voucherLimit) {
        this.voucherLimit = voucherLimit;
        return this;
    }

    public void setVoucherLimit(BigDecimal voucherLimit) {
        this.voucherLimit = voucherLimit;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public VoucherNumberControl modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public VoucherNumberControl modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public FinancialAccountYear getFinancialAccountYear() {
        return financialAccountYear;
    }

    public VoucherNumberControl financialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
        return this;
    }

    public void setFinancialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public VoucherNumberControl voucher(Voucher voucher) {
        this.voucher = voucher;
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
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
        VoucherNumberControl voucherNumberControl = (VoucherNumberControl) o;
        if (voucherNumberControl.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherNumberControl.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherNumberControl{" +
            "id=" + getId() +
            ", resetBasis='" + getResetBasis() + "'" +
            ", startVoucherNo=" + getStartVoucherNo() +
            ", voucherLimit=" + getVoucherLimit() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
