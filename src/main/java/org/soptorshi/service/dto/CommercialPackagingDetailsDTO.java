package org.soptorshi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialPackagingDetails entity.
 */
public class CommercialPackagingDetailsDTO implements Serializable {

    private Long id;

    private LocalDate proDate;

    private LocalDate expDate;

    private String shift1;

    private Double shift1Total;

    private String shift2;

    private Double shift2Total;

    private Double dayTotal;

    private Double total;

    private String createdBy;

    private LocalDate createOn;

    private String updatedBy;

    private String updatedOn;


    private Long commercialPackagingId;

    private String commercialPackagingConsignmentNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getProDate() {
        return proDate;
    }

    public void setProDate(LocalDate proDate) {
        this.proDate = proDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public String getShift1() {
        return shift1;
    }

    public void setShift1(String shift1) {
        this.shift1 = shift1;
    }

    public Double getShift1Total() {
        return shift1Total;
    }

    public void setShift1Total(Double shift1Total) {
        this.shift1Total = shift1Total;
    }

    public String getShift2() {
        return shift2;
    }

    public void setShift2(String shift2) {
        this.shift2 = shift2;
    }

    public Double getShift2Total() {
        return shift2Total;
    }

    public void setShift2Total(Double shift2Total) {
        this.shift2Total = shift2Total;
    }

    public Double getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(Double dayTotal) {
        this.dayTotal = dayTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCommercialPackagingId() {
        return commercialPackagingId;
    }

    public void setCommercialPackagingId(Long commercialPackagingId) {
        this.commercialPackagingId = commercialPackagingId;
    }

    public String getCommercialPackagingConsignmentNo() {
        return commercialPackagingConsignmentNo;
    }

    public void setCommercialPackagingConsignmentNo(String commercialPackagingConsignmentNo) {
        this.commercialPackagingConsignmentNo = commercialPackagingConsignmentNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialPackagingDetailsDTO commercialPackagingDetailsDTO = (CommercialPackagingDetailsDTO) o;
        if (commercialPackagingDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPackagingDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPackagingDetailsDTO{" +
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
            ", commercialPackaging=" + getCommercialPackagingId() +
            ", commercialPackaging='" + getCommercialPackagingConsignmentNo() + "'" +
            "}";
    }
}
