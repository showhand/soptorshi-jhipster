package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SupplyAreaManager.
 */
@Entity
@Table(name = "supply_area_manager")
@Document(indexName = "supplyareamanager")
public class SupplyAreaManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SupplyAreaManagerStatus status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyAreaManagers")
    private SupplyZone supplyZone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyAreaManagers")
    private SupplyArea supplyArea;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyAreaManagers")
    private Employee employee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyAreaManagers")
    private SupplyZoneManager supplyZoneManager;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyAreaManager createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyAreaManager createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyAreaManager updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyAreaManager updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SupplyAreaManager endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SupplyAreaManagerStatus getStatus() {
        return status;
    }

    public SupplyAreaManager status(SupplyAreaManagerStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SupplyAreaManagerStatus status) {
        this.status = status;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyAreaManager supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public SupplyArea getSupplyArea() {
        return supplyArea;
    }

    public SupplyAreaManager supplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
        return this;
    }

    public void setSupplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
    }

    public Employee getEmployee() {
        return employee;
    }

    public SupplyAreaManager employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public SupplyZoneManager getSupplyZoneManager() {
        return supplyZoneManager;
    }

    public SupplyAreaManager supplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
        return this;
    }

    public void setSupplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
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
        SupplyAreaManager supplyAreaManager = (SupplyAreaManager) o;
        if (supplyAreaManager.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyAreaManager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyAreaManager{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
