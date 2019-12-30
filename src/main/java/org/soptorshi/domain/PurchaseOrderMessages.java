package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PurchaseOrderMessages.
 */
@Entity
@Table(name = "purchase_order_messages")
@Document(indexName = "purchaseordermessages")
public class PurchaseOrderMessages implements Serializable {

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
    @JsonIgnoreProperties("purchaseOrderMessages")
    private Employee commenter;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private PurchaseOrder purchaseOrder;

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

    public PurchaseOrderMessages comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getCommentedOn() {
        return commentedOn;
    }

    public PurchaseOrderMessages commentedOn(LocalDate commentedOn) {
        this.commentedOn = commentedOn;
        return this;
    }

    public void setCommentedOn(LocalDate commentedOn) {
        this.commentedOn = commentedOn;
    }

    public Employee getCommenter() {
        return commenter;
    }

    public PurchaseOrderMessages commenter(Employee employee) {
        this.commenter = employee;
        return this;
    }

    public void setCommenter(Employee employee) {
        this.commenter = employee;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public PurchaseOrderMessages purchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
        PurchaseOrderMessages purchaseOrderMessages = (PurchaseOrderMessages) o;
        if (purchaseOrderMessages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrderMessages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrderMessages{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", commentedOn='" + getCommentedOn() + "'" +
            "}";
    }
}
