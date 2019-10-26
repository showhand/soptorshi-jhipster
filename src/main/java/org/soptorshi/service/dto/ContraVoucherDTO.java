package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ContraVoucher entity.
 */
public class ContraVoucherDTO implements Serializable {

    private Long id;

    private String voucherNo;

    private LocalDate voucherDate;

    private LocalDate postDate;

    private String modifiedBy;

    private LocalDate modifiedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContraVoucherDTO contraVoucherDTO = (ContraVoucherDTO) o;
        if (contraVoucherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contraVoucherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContraVoucherDTO{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
