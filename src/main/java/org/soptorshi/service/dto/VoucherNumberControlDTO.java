package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.VoucherResetBasis;

/**
 * A DTO for the VoucherNumberControl entity.
 */
public class VoucherNumberControlDTO implements Serializable {

    private Long id;

    private VoucherResetBasis resetBasis;

    private Integer startVoucherNo;

    private BigDecimal voucherLimit;

    private LocalDate modifiedOn;

    private String modifiedBy;


    private Long financialAccountYearId;

    private String financialAccountYearDurationStr;

    private Long voucherId;

    private String voucherName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoucherResetBasis getResetBasis() {
        return resetBasis;
    }

    public void setResetBasis(VoucherResetBasis resetBasis) {
        this.resetBasis = resetBasis;
    }

    public Integer getStartVoucherNo() {
        return startVoucherNo;
    }

    public void setStartVoucherNo(Integer startVoucherNo) {
        this.startVoucherNo = startVoucherNo;
    }

    public BigDecimal getVoucherLimit() {
        return voucherLimit;
    }

    public void setVoucherLimit(BigDecimal voucherLimit) {
        this.voucherLimit = voucherLimit;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getFinancialAccountYearId() {
        return financialAccountYearId;
    }

    public void setFinancialAccountYearId(Long financialAccountYearId) {
        this.financialAccountYearId = financialAccountYearId;
    }

    public String getFinancialAccountYearDurationStr() {
        return financialAccountYearDurationStr;
    }

    public void setFinancialAccountYearDurationStr(String financialAccountYearDurationStr) {
        this.financialAccountYearDurationStr = financialAccountYearDurationStr;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VoucherNumberControlDTO voucherNumberControlDTO = (VoucherNumberControlDTO) o;
        if (voucherNumberControlDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherNumberControlDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherNumberControlDTO{" +
            "id=" + getId() +
            ", resetBasis='" + getResetBasis() + "'" +
            ", startVoucherNo=" + getStartVoucherNo() +
            ", voucherLimit=" + getVoucherLimit() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", financialAccountYear=" + getFinancialAccountYearId() +
            ", financialAccountYear='" + getFinancialAccountYearDurationStr() + "'" +
            ", voucher=" + getVoucherId() +
            ", voucher='" + getVoucherName() + "'" +
            "}";
    }
}
