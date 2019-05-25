package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.YesOrNo;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the HolidayType entity. This class is used in HolidayTypeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /holiday-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HolidayTypeCriteria implements Serializable {
    /**
     * Class for filtering YesOrNo
     */
    public static class YesOrNoFilter extends Filter<YesOrNo> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private YesOrNoFilter moonDependency;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public YesOrNoFilter getMoonDependency() {
        return moonDependency;
    }

    public void setMoonDependency(YesOrNoFilter moonDependency) {
        this.moonDependency = moonDependency;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HolidayTypeCriteria that = (HolidayTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(moonDependency, that.moonDependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        moonDependency
        );
    }

    @Override
    public String toString() {
        return "HolidayTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (moonDependency != null ? "moonDependency=" + moonDependency + ", " : "") +
            "}";
    }

}
