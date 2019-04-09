package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Document(indexName = "attachment")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @ManyToOne
    @JsonIgnoreProperties("attachments")
    private AcademicInformation academicInformation;

    @ManyToOne
    @JsonIgnoreProperties("attachments")
    private TrainingInformation trainingInformation;

    @ManyToOne
    @JsonIgnoreProperties("attachments")
    private ExperienceInformation experienceInformation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public Attachment file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Attachment fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public AcademicInformation getAcademicInformation() {
        return academicInformation;
    }

    public Attachment academicInformation(AcademicInformation academicInformation) {
        this.academicInformation = academicInformation;
        return this;
    }

    public void setAcademicInformation(AcademicInformation academicInformation) {
        this.academicInformation = academicInformation;
    }

    public TrainingInformation getTrainingInformation() {
        return trainingInformation;
    }

    public Attachment trainingInformation(TrainingInformation trainingInformation) {
        this.trainingInformation = trainingInformation;
        return this;
    }

    public void setTrainingInformation(TrainingInformation trainingInformation) {
        this.trainingInformation = trainingInformation;
    }

    public ExperienceInformation getExperienceInformation() {
        return experienceInformation;
    }

    public Attachment experienceInformation(ExperienceInformation experienceInformation) {
        this.experienceInformation = experienceInformation;
        return this;
    }

    public void setExperienceInformation(ExperienceInformation experienceInformation) {
        this.experienceInformation = experienceInformation;
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
        Attachment attachment = (Attachment) o;
        if (attachment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attachment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
