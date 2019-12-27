package org.soptorshi.service.dto;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CommercialAttachment entity.
 */
public class CommercialAttachmentDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] file;

    private String fileContentType;

    private Long commercialPoId;

    private String commercialPoPurchaseOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Long getCommercialPoId() {
        return commercialPoId;
    }

    public void setCommercialPoId(Long commercialPoId) {
        this.commercialPoId = commercialPoId;
    }

    public String getCommercialPoPurchaseOrderNo() {
        return commercialPoPurchaseOrderNo;
    }

    public void setCommercialPoPurchaseOrderNo(String commercialPoPurchaseOrderNo) {
        this.commercialPoPurchaseOrderNo = commercialPoPurchaseOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialAttachmentDTO commercialAttachmentDTO = (CommercialAttachmentDTO) o;
        if (commercialAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialAttachmentDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", commercialPo=" + getCommercialPoId() +
            ", commercialPo='" + getCommercialPoPurchaseOrderNo() + "'" +
            "}";
    }
}
