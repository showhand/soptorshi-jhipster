package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.VoucherType;

/**
 * A DTO for the DtTransaction entity.
 */
public class DtTransactionDTO implements Serializable {

    private Long id;

    private String voucherNo;

    private LocalDate voucherDate;

    private Integer serialNo;

    private BigDecimal amount;

    private BalanceType balanceType;

    private VoucherType type;

    private String invoiceNo;

    private LocalDate invoiceDate;

    private BigDecimal fCurrency;

    private BigDecimal convFactor;

    private LocalDate postDate;

    private String narration;

    private String modifiedBy;

    private LocalDate modifiedOn;

    private String reference;


    private Long creditorLedgerId;

    private Long debtorLedgerId;

    private Long chequeRegisterId;

    private String chequeRegisterChequeNo;

    private Long accountId;

    private String accountName;

    private Long voucherId;

    private String voucherName;

    private Long currencyId;

    private String currencyNotation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getfCurrency() {
        return fCurrency;
    }

    public void setfCurrency(BigDecimal fCurrency) {
        this.fCurrency = fCurrency;
    }

    public BigDecimal getConvFactor() {
        return convFactor;
    }

    public void setConvFactor(BigDecimal convFactor) {
        this.convFactor = convFactor;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getCreditorLedgerId() {
        return creditorLedgerId;
    }

    public void setCreditorLedgerId(Long creditorLedgerId) {
        this.creditorLedgerId = creditorLedgerId;
    }

    public Long getDebtorLedgerId() {
        return debtorLedgerId;
    }

    public void setDebtorLedgerId(Long debtorLedgerId) {
        this.debtorLedgerId = debtorLedgerId;
    }

    public Long getChequeRegisterId() {
        return chequeRegisterId;
    }

    public void setChequeRegisterId(Long chequeRegisterId) {
        this.chequeRegisterId = chequeRegisterId;
    }

    public String getChequeRegisterChequeNo() {
        return chequeRegisterChequeNo;
    }

    public void setChequeRegisterChequeNo(String chequeRegisterChequeNo) {
        this.chequeRegisterChequeNo = chequeRegisterChequeNo;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long mstAccountId) {
        this.accountId = mstAccountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String mstAccountName) {
        this.accountName = mstAccountName;
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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyNotation() {
        return currencyNotation;
    }

    public void setCurrencyNotation(String currencyNotation) {
        this.currencyNotation = currencyNotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DtTransactionDTO dtTransactionDTO = (DtTransactionDTO) o;
        if (dtTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dtTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DtTransactionDTO{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", serialNo=" + getSerialNo() +
            ", amount=" + getAmount() +
            ", balanceType='" + getBalanceType() + "'" +
            ", type='" + getType() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", fCurrency=" + getfCurrency() +
            ", convFactor=" + getConvFactor() +
            ", postDate='" + getPostDate() + "'" +
            ", narration='" + getNarration() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", reference='" + getReference() + "'" +
            ", creditorLedger=" + getCreditorLedgerId() +
            ", debtorLedger=" + getDebtorLedgerId() +
            ", chequeRegister=" + getChequeRegisterId() +
            ", chequeRegister='" + getChequeRegisterChequeNo() + "'" +
            ", account=" + getAccountId() +
            ", account='" + getAccountName() + "'" +
            ", voucher=" + getVoucherId() +
            ", voucher='" + getVoucherName() + "'" +
            ", currency=" + getCurrencyId() +
            ", currency='" + getCurrencyNotation() + "'" +
            "}";
    }
}
