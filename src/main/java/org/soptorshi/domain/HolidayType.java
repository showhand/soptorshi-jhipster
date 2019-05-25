package org.soptorshi.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import org.soptorshi.domain.enumeration.YesOrNo;

/**
 * A HolidayType.
 */
@Entity
@Table(name = "holiday_type")
@Document(indexName = "holidaytype")
public class HolidayType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "moon_dependency", nullable = false)
    private YesOrNo moonDependency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public HolidayType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YesOrNo getMoonDependency() {
        return moonDependency;
    }

    public HolidayType moonDependency(YesOrNo moonDependency) {
        this.moonDependency = moonDependency;
        return this;
    }

    public void setMoonDependency(YesOrNo moonDependency) {
        this.moonDependency = moonDependency;
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
        HolidayType holidayType = (HolidayType) o;
        if (holidayType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), holidayType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HolidayType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", moonDependency='" + getMoonDependency() + "'" +
            "}";
    }
}
