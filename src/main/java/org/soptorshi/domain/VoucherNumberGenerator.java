package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import org.soptorshi.domain.enumeration.VoucherType;

/**
 * A VoucherNumberGenerator.
 */
@Entity
@Table(name = "voucher_number_generator")
@Document(indexName = "vouchernumbergenerator")
public class VoucherNumberGenerator implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "voucher_type")
    private VoucherType voucherType;

    @Column(name = "voucher_number")
    private Integer voucherNumber;

    @ManyToOne
    @JsonIgnoreProperties("voucherNumberGenerators")
    private FinancialAccountYear financialAccountYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public VoucherNumberGenerator voucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
        return this;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public Integer getVoucherNumber() {
        return voucherNumber;
    }

    public VoucherNumberGenerator voucherNumber(Integer voucherNumber) {
        this.voucherNumber = voucherNumber;
        return this;
    }

    public void setVoucherNumber(Integer voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public FinancialAccountYear getFinancialAccountYear() {
        return financialAccountYear;
    }

    public VoucherNumberGenerator financialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
        return this;
    }

    public void setFinancialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
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
        VoucherNumberGenerator voucherNumberGenerator = (VoucherNumberGenerator) o;
        if (voucherNumberGenerator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherNumberGenerator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherNumberGenerator{" +
            "id=" + getId() +
            ", voucherType='" + getVoucherType() + "'" +
            ", voucherNumber=" + getVoucherNumber() +
            "}";
    }
}
