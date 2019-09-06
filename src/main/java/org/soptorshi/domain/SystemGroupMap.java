package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.GroupType;

/**
 * A SystemGroupMap.
 */
@Entity
@Table(name = "system_group_map")
@Document(indexName = "systemgroupmap")
public class SystemGroupMap implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_type")
    private GroupType groupType;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("systemGroupMaps")
    private MstGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public SystemGroupMap groupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public SystemGroupMap modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public SystemGroupMap modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public MstGroup getGroup() {
        return group;
    }

    public SystemGroupMap group(MstGroup mstGroup) {
        this.group = mstGroup;
        return this;
    }

    public void setGroup(MstGroup mstGroup) {
        this.group = mstGroup;
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
        SystemGroupMap systemGroupMap = (SystemGroupMap) o;
        if (systemGroupMap.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemGroupMap.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemGroupMap{" +
            "id=" + getId() +
            ", groupType='" + getGroupType() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
