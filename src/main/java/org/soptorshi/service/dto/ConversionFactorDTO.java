package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ConversionFactor entity.
 */
public class ConversionFactorDTO implements Serializable {

    private Long id;

    private BigDecimal covFactor;

    private BigDecimal rcovFactor;

    private BigDecimal bcovFactor;

    private BigDecimal rbcovFactor;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long currencyId;

    private String currencyNotation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCovFactor() {
        return covFactor;
    }

    public void setCovFactor(BigDecimal covFactor) {
        this.covFactor = covFactor;
    }

    public BigDecimal getRcovFactor() {
        return rcovFactor;
    }

    public void setRcovFactor(BigDecimal rcovFactor) {
        this.rcovFactor = rcovFactor;
    }

    public BigDecimal getBcovFactor() {
        return bcovFactor;
    }

    public void setBcovFactor(BigDecimal bcovFactor) {
        this.bcovFactor = bcovFactor;
    }

    public BigDecimal getRbcovFactor() {
        return rbcovFactor;
    }

    public void setRbcovFactor(BigDecimal rbcovFactor) {
        this.rbcovFactor = rbcovFactor;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyNotation() {
        return currencyNotation;
    }

    public void setCurrencyNotation(String currencyNotation) {
        this.currencyNotation = currencyNotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConversionFactorDTO conversionFactorDTO = (ConversionFactorDTO) o;
        if (conversionFactorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversionFactorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConversionFactorDTO{" +
            "id=" + getId() +
            ", covFactor=" + getCovFactor() +
            ", rcovFactor=" + getRcovFactor() +
            ", bcovFactor=" + getBcovFactor() +
            ", rbcovFactor=" + getRbcovFactor() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", currency=" + getCurrencyId() +
            ", currency='" + getCurrencyNotation() + "'" +
            "}";
    }
}
