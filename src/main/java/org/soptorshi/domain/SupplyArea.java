package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SupplyArea.
 */
@Entity
@Table(name = "supply_area")
@Document(indexName = "supplyarea")
public class SupplyArea implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "area_name", nullable = false)
    private String areaName;

    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("supplyAreas")
    private SupplyZone supplyZone;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public SupplyArea areaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public SupplyArea areaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyArea createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyArea createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyArea updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyArea updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyArea supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
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
        SupplyArea supplyArea = (SupplyArea) o;
        if (supplyArea.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyArea.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyArea{" +
            "id=" + getId() +
            ", areaName='" + getAreaName() + "'" +
            ", areaCode='" + getAreaCode() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
