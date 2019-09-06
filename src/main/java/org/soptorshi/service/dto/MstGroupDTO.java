package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ReservedFlag;

/**
 * A DTO for the MstGroup entity.
 */
public class MstGroupDTO implements Serializable {

    private Long id;

    private String name;

    private Long mainGroup;

    private ReservedFlag reservedFlag;

    private String modifiedBy;

    private LocalDate modifiedOn;


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

    public Long getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(Long mainGroup) {
        this.mainGroup = mainGroup;
    }

    public ReservedFlag getReservedFlag() {
        return reservedFlag;
    }

    public void setReservedFlag(ReservedFlag reservedFlag) {
        this.reservedFlag = reservedFlag;
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

        MstGroupDTO mstGroupDTO = (MstGroupDTO) o;
        if (mstGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mstGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MstGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mainGroup=" + getMainGroup() +
            ", reservedFlag='" + getReservedFlag() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
