package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SupplyChallan.
 */
@Entity
@Table(name = "supply_challan")
@Document(indexName = "supplychallan")
public class SupplyChallan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "challan_no", nullable = false)
    private String challanNo;

    @Column(name = "date_of_challan")
    private LocalDate dateOfChallan;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyChallans")
    private SupplyZone supplyZone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyChallans")
    private SupplyZoneManager supplyZoneManager;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyChallans")
    private SupplyArea supplyArea;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyChallans")
    private SupplyAreaManager supplyAreaManager;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyChallans")
    private SupplySalesRepresentative supplySalesRepresentative;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyChallans")
    private SupplyShop supplyShop;

    @ManyToOne
    @JsonIgnoreProperties("supplyChallans")
    private SupplyOrder supplyOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public SupplyChallan challanNo(String challanNo) {
        this.challanNo = challanNo;
        return this;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public LocalDate getDateOfChallan() {
        return dateOfChallan;
    }

    public SupplyChallan dateOfChallan(LocalDate dateOfChallan) {
        this.dateOfChallan = dateOfChallan;
        return this;
    }

    public void setDateOfChallan(LocalDate dateOfChallan) {
        this.dateOfChallan = dateOfChallan;
    }

    public String getRemarks() {
        return remarks;
    }

    public SupplyChallan remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyChallan createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyChallan createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyChallan updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyChallan updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyChallan supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public SupplyZoneManager getSupplyZoneManager() {
        return supplyZoneManager;
    }

    public SupplyChallan supplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
        return this;
    }

    public void setSupplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
    }

    public SupplyArea getSupplyArea() {
        return supplyArea;
    }

    public SupplyChallan supplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
        return this;
    }

    public void setSupplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
    }

    public SupplyAreaManager getSupplyAreaManager() {
        return supplyAreaManager;
    }

    public SupplyChallan supplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
        return this;
    }

    public void setSupplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
    }

    public SupplySalesRepresentative getSupplySalesRepresentative() {
        return supplySalesRepresentative;
    }

    public SupplyChallan supplySalesRepresentative(SupplySalesRepresentative supplySalesRepresentative) {
        this.supplySalesRepresentative = supplySalesRepresentative;
        return this;
    }

    public void setSupplySalesRepresentative(SupplySalesRepresentative supplySalesRepresentative) {
        this.supplySalesRepresentative = supplySalesRepresentative;
    }

    public SupplyShop getSupplyShop() {
        return supplyShop;
    }

    public SupplyChallan supplyShop(SupplyShop supplyShop) {
        this.supplyShop = supplyShop;
        return this;
    }

    public void setSupplyShop(SupplyShop supplyShop) {
        this.supplyShop = supplyShop;
    }

    public SupplyOrder getSupplyOrder() {
        return supplyOrder;
    }

    public SupplyChallan supplyOrder(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
        return this;
    }

    public void setSupplyOrder(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
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
        SupplyChallan supplyChallan = (SupplyChallan) o;
        if (supplyChallan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyChallan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyChallan{" +
            "id=" + getId() +
            ", challanNo='" + getChallanNo() + "'" +
            ", dateOfChallan='" + getDateOfChallan() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
