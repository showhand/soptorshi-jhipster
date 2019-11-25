package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the SalaryMessages entity.
 */
public class SalaryMessagesDTO implements Serializable {

    private Long id;

    @Lob
    private String comments;


    private Long commenterId;

    private String commenterFullName;

    private Long monthlySalaryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(Long employeeId) {
        this.commenterId = employeeId;
    }

    public String getCommenterFullName() {
        return commenterFullName;
    }

    public void setCommenterFullName(String employeeFullName) {
        this.commenterFullName = employeeFullName;
    }

    public Long getMonthlySalaryId() {
        return monthlySalaryId;
    }

    public void setMonthlySalaryId(Long monthlySalaryId) {
        this.monthlySalaryId = monthlySalaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalaryMessagesDTO salaryMessagesDTO = (SalaryMessagesDTO) o;
        if (salaryMessagesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salaryMessagesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalaryMessagesDTO{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", commenter=" + getCommenterId() +
            ", commenter='" + getCommenterFullName() + "'" +
            ", monthlySalary=" + getMonthlySalaryId() +
            "}";
    }
}
