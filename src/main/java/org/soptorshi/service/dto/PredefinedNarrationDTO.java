package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PredefinedNarration entity.
 */
public class PredefinedNarrationDTO implements Serializable {

    private Long id;

    private String narration;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long voucherId;

    private String voucherName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PredefinedNarrationDTO predefinedNarrationDTO = (PredefinedNarrationDTO) o;
        if (predefinedNarrationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), predefinedNarrationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PredefinedNarrationDTO{" +
            "id=" + getId() +
            ", narration='" + getNarration() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", voucher=" + getVoucherId() +
            ", voucher='" + getVoucherName() + "'" +
            "}";
    }
}
