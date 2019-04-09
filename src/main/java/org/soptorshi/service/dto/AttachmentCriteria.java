package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Attachment entity. This class is used in AttachmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /attachments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttachmentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter academicInformationId;

    private LongFilter trainingInformationId;

    private LongFilter experienceInformationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAcademicInformationId() {
        return academicInformationId;
    }

    public void setAcademicInformationId(LongFilter academicInformationId) {
        this.academicInformationId = academicInformationId;
    }

    public LongFilter getTrainingInformationId() {
        return trainingInformationId;
    }

    public void setTrainingInformationId(LongFilter trainingInformationId) {
        this.trainingInformationId = trainingInformationId;
    }

    public LongFilter getExperienceInformationId() {
        return experienceInformationId;
    }

    public void setExperienceInformationId(LongFilter experienceInformationId) {
        this.experienceInformationId = experienceInformationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttachmentCriteria that = (AttachmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(academicInformationId, that.academicInformationId) &&
            Objects.equals(trainingInformationId, that.trainingInformationId) &&
            Objects.equals(experienceInformationId, that.experienceInformationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        academicInformationId,
        trainingInformationId,
        experienceInformationId
        );
    }

    @Override
    public String toString() {
        return "AttachmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (academicInformationId != null ? "academicInformationId=" + academicInformationId + ", " : "") +
                (trainingInformationId != null ? "trainingInformationId=" + trainingInformationId + ", " : "") +
                (experienceInformationId != null ? "experienceInformationId=" + experienceInformationId + ", " : "") +
            "}";
    }

}
