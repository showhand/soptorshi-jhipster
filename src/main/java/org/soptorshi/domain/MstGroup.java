package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.ReservedFlag;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MstGroup.
 */
@Entity
@Table(name = "mst_group")
@Document(indexName = "mstgroup")
@EntityListeners(AuditingEntityListener.class)
public class MstGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "main_group")
    private Long mainGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "reserved_flag")
    private ReservedFlag reservedFlag;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_on")
    @LastModifiedDate
    private LocalDate modifiedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MstGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMainGroup() {
        return mainGroup;
    }

    public MstGroup mainGroup(Long mainGroup) {
        this.mainGroup = mainGroup;
        return this;
    }

    public void setMainGroup(Long mainGroup) {
        this.mainGroup = mainGroup;
    }

    public ReservedFlag getReservedFlag() {
        return reservedFlag;
    }

    public MstGroup reservedFlag(ReservedFlag reservedFlag) {
        this.reservedFlag = reservedFlag;
        return this;
    }

    public void setReservedFlag(ReservedFlag reservedFlag) {
        this.reservedFlag = reservedFlag;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public MstGroup modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public MstGroup modifiedOn(LocalDate modifiedOn) {
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
        MstGroup mstGroup = (MstGroup) o;
        if (mstGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mstGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MstGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mainGroup=" + getMainGroup() +
            ", reservedFlag='" + getReservedFlag() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
