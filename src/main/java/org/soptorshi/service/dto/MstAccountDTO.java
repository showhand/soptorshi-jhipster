package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ReservedFlag;

/**
 * A DTO for the MstAccount entity.
 */
public class MstAccountDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private BigDecimal yearOpenBalance;

    private BigDecimal yearCloseBalance;

    private ReservedFlag reservedFlag;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long groupId;

    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getYearOpenBalance() {
        return yearOpenBalance;
    }

    public void setYearOpenBalance(BigDecimal yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
    }

    public BigDecimal getYearCloseBalance() {
        return yearCloseBalance;
    }

    public void setYearCloseBalance(BigDecimal yearCloseBalance) {
        this.yearCloseBalance = yearCloseBalance;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long mstGroupId) {
        this.groupId = mstGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String mstGroupName) {
        this.groupName = mstGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MstAccountDTO mstAccountDTO = (MstAccountDTO) o;
        if (mstAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mstAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MstAccountDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", yearOpenBalance=" + getYearOpenBalance() +
            ", yearCloseBalance=" + getYearCloseBalance() +
            ", reservedFlag='" + getReservedFlag() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", group=" + getGroupId() +
            ", group='" + getGroupName() + "'" +
            "}";
    }
}
