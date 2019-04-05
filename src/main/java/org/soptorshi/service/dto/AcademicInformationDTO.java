package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AcademicInformation entity.
 */
public class AcademicInformationDTO implements Serializable {

    private Long id;

    private String degree;

    private String boardOrUniversity;

    private Integer passingYear;

    private String group;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBoardOrUniversity() {
        return boardOrUniversity;
    }

    public void setBoardOrUniversity(String boardOrUniversity) {
        this.boardOrUniversity = boardOrUniversity;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AcademicInformationDTO academicInformationDTO = (AcademicInformationDTO) o;
        if (academicInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), academicInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AcademicInformationDTO{" +
            "id=" + getId() +
            ", degree='" + getDegree() + "'" +
            ", boardOrUniversity='" + getBoardOrUniversity() + "'" +
            ", passingYear=" + getPassingYear() +
            ", group='" + getGroup() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
