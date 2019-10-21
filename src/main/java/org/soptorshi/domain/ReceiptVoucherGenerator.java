package org.soptorshi.domain;



import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ReceiptVoucherGenerator.
 */
@Entity
@Table(name = "receipt_voucher_generator")
@Document(indexName = "receiptvouchergenerator")
public class ReceiptVoucherGenerator implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public ReceiptVoucherGenerator lastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
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
        ReceiptVoucherGenerator receiptVoucherGenerator = (ReceiptVoucherGenerator) o;
        if (receiptVoucherGenerator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), receiptVoucherGenerator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReceiptVoucherGenerator{" +
            "id=" + getId() +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
