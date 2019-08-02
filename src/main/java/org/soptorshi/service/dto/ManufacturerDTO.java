package org.soptorshi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Manufacturer entity.
 */
public class ManufacturerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String contact;

    private String email;

    private String address;

    private String description;

    private String remarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManufacturerDTO manufacturerDTO = (ManufacturerDTO) o;
        if (manufacturerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), manufacturerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ManufacturerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", description='" + getDescription() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
