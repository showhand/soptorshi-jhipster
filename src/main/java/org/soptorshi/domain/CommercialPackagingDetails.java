package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialPackagingDetails.
 */
@Entity
@Table(name = "commercial_packaging_details")
@Document(indexName = "commercialpackagingdetails")
public class CommercialPackagingDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pro_date")
    private LocalDate proDate;

    @Column(name = "exp_date")
    private LocalDate expDate;

    @Column(name = "shift_1")
    private String shift1;

    @Column(name = "shift_1_total")
    private Double shift1Total;

    @Column(name = "shift_2")
    private String shift2;

    @Column(name = "shift_2_total")
    private Double shift2Total;

    @Column(name = "day_total")
    private Double dayTotal;

    @Column(name = "total")
    private Double total;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_on")
    private LocalDate createOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("commercialPackagingDetails")
    private CommercialPackaging commercialPackaging;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getProDate() {
        return proDate;
    }

    public CommercialPackagingDetails proDate(LocalDate proDate) {
        this.proDate = proDate;
        return this;
    }

    public void setProDate(LocalDate proDate) {
        this.proDate = proDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public CommercialPackagingDetails expDate(LocalDate expDate) {
        this.expDate = expDate;
        return this;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public String getShift1() {
        return shift1;
    }

    public CommercialPackagingDetails shift1(String shift1) {
        this.shift1 = shift1;
        return this;
    }

    public void setShift1(String shift1) {
        this.shift1 = shift1;
    }

    public Double getShift1Total() {
        return shift1Total;
    }

    public CommercialPackagingDetails shift1Total(Double shift1Total) {
        this.shift1Total = shift1Total;
        return this;
    }

    public void setShift1Total(Double shift1Total) {
        this.shift1Total = shift1Total;
    }

    public String getShift2() {
        return shift2;
    }

    public CommercialPackagingDetails shift2(String shift2) {
        this.shift2 = shift2;
        return this;
    }

    public void setShift2(String shift2) {
        this.shift2 = shift2;
    }

    public Double getShift2Total() {
        return shift2Total;
    }

    public CommercialPackagingDetails shift2Total(Double shift2Total) {
        this.shift2Total = shift2Total;
        return this;
    }

    public void setShift2Total(Double shift2Total) {
        this.shift2Total = shift2Total;
    }

    public Double getDayTotal() {
        return dayTotal;
    }

    public CommercialPackagingDetails dayTotal(Double dayTotal) {
        this.dayTotal = dayTotal;
        return this;
    }

    public void setDayTotal(Double dayTotal) {
        this.dayTotal = dayTotal;
    }

    public Double getTotal() {
        return total;
    }

    public CommercialPackagingDetails total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPackagingDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public CommercialPackagingDetails createOn(LocalDate createOn) {
        this.createOn = createOn;
        return this;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPackagingDetails updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPackagingDetails updatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPackaging getCommercialPackaging() {
        return commercialPackaging;
    }

    public CommercialPackagingDetails commercialPackaging(CommercialPackaging commercialPackaging) {
        this.commercialPackaging = commercialPackaging;
        return this;
    }

    public void setCommercialPackaging(CommercialPackaging commercialPackaging) {
        this.commercialPackaging = commercialPackaging;
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
        CommercialPackagingDetails commercialPackagingDetails = (CommercialPackagingDetails) o;
        if (commercialPackagingDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPackagingDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPackagingDetails{" +
            "id=" + getId() +
            ", proDate='" + getProDate() + "'" +
            ", expDate='" + getExpDate() + "'" +
            ", shift1='" + getShift1() + "'" +
            ", shift1Total=" + getShift1Total() +
            ", shift2='" + getShift2() + "'" +
            ", shift2Total=" + getShift2Total() +
            ", dayTotal=" + getDayTotal() +
            ", total=" + getTotal() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
