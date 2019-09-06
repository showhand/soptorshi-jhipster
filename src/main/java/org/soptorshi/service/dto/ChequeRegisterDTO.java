package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ChequeRegister entity.
 */
public class ChequeRegisterDTO implements Serializable {

    private Long id;

    private String chequeNo;

    private LocalDate chequeDate;

    private String status;

    private LocalDate realizationDate;

    private String modifiedBy;

    private LocalDate modifiedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public LocalDate getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(LocalDate chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(LocalDate realizationDate) {
        this.realizationDate = realizationDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChequeRegisterDTO chequeRegisterDTO = (ChequeRegisterDTO) o;
        if (chequeRegisterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chequeRegisterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChequeRegisterDTO{" +
            "id=" + getId() +
            ", chequeNo='" + getChequeNo() + "'" +
            ", chequeDate='" + getChequeDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", realizationDate='" + getRealizationDate() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
