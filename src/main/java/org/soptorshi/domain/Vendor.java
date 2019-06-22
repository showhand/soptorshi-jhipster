package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.soptorshi.domain.enumeration.VendorRemarks;

/**
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
@Document(indexName = "vendor")
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "remarks")
    private VendorRemarks remarks;

    @OneToMany(mappedBy = "vendor")
    private Set<VendorContactPerson> vendorContactPeople = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Vendor companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public Vendor description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VendorRemarks getRemarks() {
        return remarks;
    }

    public Vendor remarks(VendorRemarks remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(VendorRemarks remarks) {
        this.remarks = remarks;
    }

    public Set<VendorContactPerson> getVendorContactPeople() {
        return vendorContactPeople;
    }

    public Vendor vendorContactPeople(Set<VendorContactPerson> vendorContactPeople) {
        this.vendorContactPeople = vendorContactPeople;
        return this;
    }

    public Vendor addVendorContactPerson(VendorContactPerson vendorContactPerson) {
        this.vendorContactPeople.add(vendorContactPerson);
        vendorContactPerson.setVendor(this);
        return this;
    }

    public Vendor removeVendorContactPerson(VendorContactPerson vendorContactPerson) {
        this.vendorContactPeople.remove(vendorContactPerson);
        vendorContactPerson.setVendor(null);
        return this;
    }

    public void setVendorContactPeople(Set<VendorContactPerson> vendorContactPeople) {
        this.vendorContactPeople = vendorContactPeople;
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
        Vendor vendor = (Vendor) o;
        if (vendor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vendor{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", description='" + getDescription() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
