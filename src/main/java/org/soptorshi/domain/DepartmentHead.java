package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DepartmentHead.
 */
@Entity
@Table(name = "department_head",
uniqueConstraints = {
    @UniqueConstraint(columnNames = {"office_id","department_id","employee_id"}),
    @UniqueConstraint(columnNames = {"department_id","employee_id"})
})
@Document(indexName = "departmenthead")
public class DepartmentHead implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("departmentHeads")
    private Office office;

    @ManyToOne
    @JsonIgnoreProperties("departmentHeads")
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties("departmentHeads")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Office getOffice() {
        return office;
    }

    public DepartmentHead office(Office office) {
        this.office = office;
        return this;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Department getDepartment() {
        return department;
    }

    public DepartmentHead department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public DepartmentHead employee(Employee employee) {
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
        DepartmentHead departmentHead = (DepartmentHead) o;
        if (departmentHead.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), departmentHead.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepartmentHead{" +
            "id=" + getId() +
            "}";
    }
}
