package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PredefinedNarration.
 */
@Entity
@Table(name = "predefined_narration")
@Document(indexName = "predefinednarration")
public class PredefinedNarration implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "narration")
    private String narration;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("predefinedNarrations")
    private Voucher voucher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNarration() {
        return narration;
    }

    public PredefinedNarration narration(String narration) {
        this.narration = narration;
        return this;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public PredefinedNarration modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public PredefinedNarration modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public PredefinedNarration voucher(Voucher voucher) {
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
        PredefinedNarration predefinedNarration = (PredefinedNarration) o;
        if (predefinedNarration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), predefinedNarration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PredefinedNarration{" +
            "id=" + getId() +
            ", narration='" + getNarration() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
