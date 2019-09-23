package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.ReservedFlag;

/**
 * A MstAccount.
 */
@Entity
@Table(name = "mst_account")
@Document(indexName = "mstaccount")
public class MstAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "reserved_flag")
    private ReservedFlag reservedFlag;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("mstAccounts")
    private MstGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public MstAccount code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public MstAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReservedFlag getReservedFlag() {
        return reservedFlag;
    }

    public MstAccount reservedFlag(ReservedFlag reservedFlag) {
        this.reservedFlag = reservedFlag;
        return this;
    }

    public void setReservedFlag(ReservedFlag reservedFlag) {
        this.reservedFlag = reservedFlag;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public MstAccount modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public MstAccount modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public MstGroup getGroup() {
        return group;
    }

    public MstAccount group(MstGroup mstGroup) {
        this.group = mstGroup;
        return this;
    }

    public void setGroup(MstGroup mstGroup) {
        this.group = mstGroup;
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
        MstAccount mstAccount = (MstAccount) o;
        if (mstAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mstAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MstAccount{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", reservedFlag='" + getReservedFlag() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}