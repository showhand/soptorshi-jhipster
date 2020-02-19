package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.PaidOrUnPaid;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A LeaveType.
 */
@Entity
@Table(name = "leave_type")
@Document(indexName = "leavetype")
public class LeaveType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "paid_leave")
    private PaidOrUnPaid paidLeave;

    @Column(name = "maximum_number_of_days")
    private Integer maximumNumberOfDays;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

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

    public LeaveType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaidOrUnPaid getPaidLeave() {
        return paidLeave;
    }

    public LeaveType paidLeave(PaidOrUnPaid paidLeave) {
        this.paidLeave = paidLeave;
        return this;
    }

    public void setPaidLeave(PaidOrUnPaid paidLeave) {
        this.paidLeave = paidLeave;
    }

    public Integer getMaximumNumberOfDays() {
        return maximumNumberOfDays;
    }

    public LeaveType maximumNumberOfDays(Integer maximumNumberOfDays) {
        this.maximumNumberOfDays = maximumNumberOfDays;
        return this;
    }

    public void setMaximumNumberOfDays(Integer maximumNumberOfDays) {
        this.maximumNumberOfDays = maximumNumberOfDays;
    }

    public String getDescription() {
        return description;
    }

    public LeaveType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LeaveType createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public LeaveType createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public LeaveType updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public LeaveType updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
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
        LeaveType leaveType = (LeaveType) o;
        if (leaveType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", paidLeave='" + getPaidLeave() + "'" +
            ", maximumNumberOfDays=" + getMaximumNumberOfDays() +
            ", description='" + getDescription() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
