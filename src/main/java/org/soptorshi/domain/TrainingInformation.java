package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TrainingInformation.
 */
@Entity
@Table(name = "training_information")
@Document(indexName = "traininginformation")
public class TrainingInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "subject")
    private String subject;

    @Column(name = "jhi_organization")
    private String organization;

    @OneToMany(mappedBy = "trainingInformation")
    private Set<Attachment> attachments = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("trainingInformations")
    private Employee employee;

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

    public TrainingInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public TrainingInformation subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrganization() {
        return organization;
    }

    public TrainingInformation organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public TrainingInformation attachments(Set<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public TrainingInformation addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setTrainingInformation(this);
        return this;
    }

    public TrainingInformation removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setTrainingInformation(null);
        return this;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Employee getEmployee() {
        return employee;
    }

    public TrainingInformation employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        TrainingInformation trainingInformation = (TrainingInformation) o;
        if (trainingInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainingInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainingInformation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subject='" + getSubject() + "'" +
            ", organization='" + getOrganization() + "'" +
            "}";
    }
}
