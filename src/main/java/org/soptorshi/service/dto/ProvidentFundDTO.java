package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProvidentFund entity.
 */
public class ProvidentFundDTO implements Serializable {

    private Long id;

    private LocalDate startDate;

    private Double rate;

    private Boolean status;

    private Long modifiedBy;

    private LocalDate modifiedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProvidentFundDTO providentFundDTO = (ProvidentFundDTO) o;
        if (providentFundDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providentFundDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProvidentFundDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", rate=" + getRate() +
            ", status='" + isStatus() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
