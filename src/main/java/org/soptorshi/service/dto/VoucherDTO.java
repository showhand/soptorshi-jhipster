package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Voucher entity.
 */
public class VoucherDTO implements Serializable {

    private Long id;

    private String name;

    private String shortName;

    private String modifiedOn;

    private Long modifiedBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VoucherDTO voucherDTO = (VoucherDTO) o;
        if (voucherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            "}";
    }
}
