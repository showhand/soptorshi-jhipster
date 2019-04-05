package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReferenceInformation.
 */
@Entity
@Table(name = "reference_information")
@Document(indexName = "referenceinformation")
public class ReferenceInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "jhi_organization")
    private String organization;

    @Column(name = "contact_number")
    private String contactNumber;

    @ManyToOne
    @JsonIgnoreProperties("referenceInformations")
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

    public ReferenceInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public ReferenceInformation designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganization() {
        return organization;
    }

    public ReferenceInformation organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public ReferenceInformation contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ReferenceInformation employee(Employee employee) {
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
        ReferenceInformation referenceInformation = (ReferenceInformation) o;
        if (referenceInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referenceInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferenceInformation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            "}";
    }
}
