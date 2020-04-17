package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SupplyZoneManager.
 */
@Entity
@Table(name = "supply_zone_manager")
@Document(indexName = "supplyzonemanager")
public class SupplyZoneManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SupplyZoneManagerStatus status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyZoneManagers")
    private SupplyZone supplyZone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyZoneManagers")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SupplyZoneManager endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyZoneManager createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyZoneManager createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyZoneManager updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyZoneManager updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplyZoneManagerStatus getStatus() {
        return status;
    }

    public SupplyZoneManager status(SupplyZoneManagerStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SupplyZoneManagerStatus status) {
        this.status = status;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyZoneManager supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public Employee getEmployee() {
        return employee;
    }

    public SupplyZoneManager employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        SupplyZoneManager supplyZoneManager = (SupplyZoneManager) o;
        if (supplyZoneManager.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyZoneManager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyZoneManager{" +
            "id=" + getId() +
            ", endDate='" + getEndDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
