package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.BalanceType;

import org.soptorshi.domain.enumeration.VoucherType;

import org.soptorshi.domain.enumeration.InstrumentType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A DtTransaction.
 */
@Entity
@Table(name = "dt_transaction")
@Document(indexName = "dttransaction")
@EntityListeners(AuditingEntityListener.class)
public class DtTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_no")
    private String voucherNo;

    @Column(name = "voucher_date")
    private LocalDate voucherDate;

    @Column(name = "serial_no")
    private Integer serialNo;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "balance_type")
    private BalanceType balanceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private VoucherType type;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "instrument_type")
    private InstrumentType instrumentType;

    @Column(name = "instrument_no")
    private String instrumentNo;

    @Column(name = "instrument_date")
    private LocalDate instrumentDate;

    @Column(name = "f_currency", precision = 10, scale = 2)
    private BigDecimal fCurrency;

    @Column(name = "conv_factor", precision = 10, scale = 2)
    private BigDecimal convFactor;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Column(name = "narration")
    private String narration;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_on")
    @LastModifiedDate
    private LocalDate modifiedOn;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JsonIgnoreProperties("dtTransactions")
    private MstAccount account;

    @ManyToOne
    @JsonIgnoreProperties("dtTransactions")
    private Voucher voucher;

    @ManyToOne
    @JsonIgnoreProperties("dtTransactions")
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

    public DtTransaction voucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public DtTransaction voucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
        return this;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public DtTransaction serialNo(Integer serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DtTransaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public DtTransaction balanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
        return this;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public VoucherType getType() {
        return type;
    }

    public DtTransaction type(VoucherType type) {
        this.type = type;
        return this;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public DtTransaction invoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public DtTransaction invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    public DtTransaction instrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
        return this;
    }

    public void setInstrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getInstrumentNo() {
        return instrumentNo;
    }

    public DtTransaction instrumentNo(String instrumentNo) {
        this.instrumentNo = instrumentNo;
        return this;
    }

    public void setInstrumentNo(String instrumentNo) {
        this.instrumentNo = instrumentNo;
    }

    public LocalDate getInstrumentDate() {
        return instrumentDate;
    }

    public DtTransaction instrumentDate(LocalDate instrumentDate) {
        this.instrumentDate = instrumentDate;
        return this;
    }

    public void setInstrumentDate(LocalDate instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public BigDecimal getfCurrency() {
        return fCurrency;
    }

    public DtTransaction fCurrency(BigDecimal fCurrency) {
        this.fCurrency = fCurrency;
        return this;
    }

    public void setfCurrency(BigDecimal fCurrency) {
        this.fCurrency = fCurrency;
    }

    public BigDecimal getConvFactor() {
        return convFactor;
    }

    public DtTransaction convFactor(BigDecimal convFactor) {
        this.convFactor = convFactor;
        return this;
    }

    public void setConvFactor(BigDecimal convFactor) {
        this.convFactor = convFactor;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public DtTransaction postDate(LocalDate postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getNarration() {
        return narration;
    }

    public DtTransaction narration(String narration) {
        this.narration = narration;
        return this;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DtTransaction modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public DtTransaction modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getReference() {
        return reference;
    }

    public DtTransaction reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public MstAccount getAccount() {
        return account;
    }

    public DtTransaction account(MstAccount mstAccount) {
        this.account = mstAccount;
        return this;
    }

    public void setAccount(MstAccount mstAccount) {
        this.account = mstAccount;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public DtTransaction voucher(Voucher voucher) {
        this.voucher = voucher;
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Currency getCurrency() {
        return currency;
    }

    public DtTransaction currency(Currency currency) {
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
        DtTransaction dtTransaction = (DtTransaction) o;
        if (dtTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dtTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DtTransaction{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", serialNo=" + getSerialNo() +
            ", amount=" + getAmount() +
            ", balanceType='" + getBalanceType() + "'" +
            ", type='" + getType() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", instrumentType='" + getInstrumentType() + "'" +
            ", instrumentNo='" + getInstrumentNo() + "'" +
            ", instrumentDate='" + getInstrumentDate() + "'" +
            ", fCurrency=" + getfCurrency() +
            ", convFactor=" + getConvFactor() +
            ", postDate='" + getPostDate() + "'" +
            ", narration='" + getNarration() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
