package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RequisitionMessages.
 */
@Entity
@Table(name = "requisition_messages")
@Document(indexName = "requisitionmessages")
public class RequisitionMessages implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Column(name = "commented_on")
    private LocalDate commentedOn;

    @ManyToOne
    @JsonIgnoreProperties("requisitionMessages")
    private Employee commenter;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Requisition requisition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public RequisitionMessages comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getCommentedOn() {
        return commentedOn;
    }

    public RequisitionMessages commentedOn(LocalDate commentedOn) {
        this.commentedOn = commentedOn;
        return this;
    }

    public void setCommentedOn(LocalDate commentedOn) {
        this.commentedOn = commentedOn;
    }

    public Employee getCommenter() {
        return commenter;
    }

    public RequisitionMessages commenter(Employee employee) {
        this.commenter = employee;
        return this;
    }

    public void setCommenter(Employee employee) {
        this.commenter = employee;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public RequisitionMessages requisition(Requisition requisition) {
        this.requisition = requisition;
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
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
        RequisitionMessages requisitionMessages = (RequisitionMessages) o;
        if (requisitionMessages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisitionMessages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequisitionMessages{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", commentedOn='" + getCommentedOn() + "'" +
            "}";
    }
}
