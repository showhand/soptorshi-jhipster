package org.soptorshi.domain;



import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A JournalVoucher.
 */
@Entity
@Table(name = "journal_voucher")
@Document(indexName = "journalvoucher")
public class JournalVoucher implements Serializable {

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

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

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

    public JournalVoucher voucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public JournalVoucher voucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
        return this;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public JournalVoucher postDate(LocalDate postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public JournalVoucher modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public JournalVoucher modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
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
        JournalVoucher journalVoucher = (JournalVoucher) o;
        if (journalVoucher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalVoucher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalVoucher{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
