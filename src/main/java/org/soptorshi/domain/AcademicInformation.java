package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AcademicInformation.
 */
@Entity
@Table(name = "academic_information")
@Document(indexName = "academicinformation")
public class AcademicInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_degree")
    private String degree;

    @Column(name = "board_or_university")
    private String boardOrUniversity;

    @Column(name = "passing_year")
    private Integer passingYear;

    @Column(name = "jhi_group")
    private String group;

    @ManyToOne
    @JsonIgnoreProperties("academicInformations")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public AcademicInformation degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBoardOrUniversity() {
        return boardOrUniversity;
    }

    public AcademicInformation boardOrUniversity(String boardOrUniversity) {
        this.boardOrUniversity = boardOrUniversity;
        return this;
    }

    public void setBoardOrUniversity(String boardOrUniversity) {
        this.boardOrUniversity = boardOrUniversity;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public AcademicInformation passingYear(Integer passingYear) {
        this.passingYear = passingYear;
        return this;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public String getGroup() {
        return group;
    }

    public AcademicInformation group(String group) {
        this.group = group;
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Employee getEmployee() {
        return employee;
    }

    public AcademicInformation employee(Employee employee) {
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
        AcademicInformation academicInformation = (AcademicInformation) o;
        if (academicInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), academicInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AcademicInformation{" +
            "id=" + getId() +
            ", degree='" + getDegree() + "'" +
            ", boardOrUniversity='" + getBoardOrUniversity() + "'" +
            ", passingYear=" + getPassingYear() +
            ", group='" + getGroup() + "'" +
            "}";
    }
}
