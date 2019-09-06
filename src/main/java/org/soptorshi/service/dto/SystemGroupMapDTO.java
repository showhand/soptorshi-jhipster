package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.GroupType;

/**
 * A DTO for the SystemGroupMap entity.
 */
public class SystemGroupMapDTO implements Serializable {

    private Long id;

    private GroupType groupType;

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

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
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

        SystemGroupMapDTO systemGroupMapDTO = (SystemGroupMapDTO) o;
        if (systemGroupMapDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemGroupMapDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemGroupMapDTO{" +
            "id=" + getId() +
            ", groupType='" + getGroupType() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", group=" + getGroupId() +
            ", group='" + getGroupName() + "'" +
            "}";
    }
}
