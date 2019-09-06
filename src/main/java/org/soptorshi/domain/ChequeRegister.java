package org.soptorshi.domain;



import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ChequeRegister.
 */
@Entity
@Table(name = "cheque_register")
@Document(indexName = "chequeregister")
public class ChequeRegister implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cheque_no")
    private String chequeNo;

    @Column(name = "cheque_date")
    private LocalDate chequeDate;

    @Column(name = "status")
    private String status;

    @Column(name = "realization_date")
    private LocalDate realizationDate;

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

    public String getChequeNo() {
        return chequeNo;
    }

    public ChequeRegister chequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
        return this;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public LocalDate getChequeDate() {
        return chequeDate;
    }

    public ChequeRegister chequeDate(LocalDate chequeDate) {
        this.chequeDate = chequeDate;
        return this;
    }

    public void setChequeDate(LocalDate chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getStatus() {
        return status;
    }

    public ChequeRegister status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRealizationDate() {
        return realizationDate;
    }

    public ChequeRegister realizationDate(LocalDate realizationDate) {
        this.realizationDate = realizationDate;
        return this;
    }

    public void setRealizationDate(LocalDate realizationDate) {
        this.realizationDate = realizationDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ChequeRegister modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public ChequeRegister modifiedOn(LocalDate modifiedOn) {
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
        ChequeRegister chequeRegister = (ChequeRegister) o;
        if (chequeRegister.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chequeRegister.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChequeRegister{" +
            "id=" + getId() +
            ", chequeNo='" + getChequeNo() + "'" +
            ", chequeDate='" + getChequeDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", realizationDate='" + getRealizationDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
