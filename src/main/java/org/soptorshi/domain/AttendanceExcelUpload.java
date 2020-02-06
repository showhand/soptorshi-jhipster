package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.AttendanceType;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A AttendanceExcelUpload.
 */
@Entity
@Table(name = "attendance_excel_upload")
@Document(indexName = "attendanceexcelupload")
public class AttendanceExcelUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(name = "jhi_file", nullable = false)
    private byte[] file;

    @Column(name = "jhi_file_content_type", nullable = false)
    private String fileContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private AttendanceType type;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @OneToMany(mappedBy = "attendanceExcelUpload")
    private Set<Attendance> attendances = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public AttendanceExcelUpload file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public AttendanceExcelUpload fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public AttendanceType getType() {
        return type;
    }

    public AttendanceExcelUpload type(AttendanceType type) {
        this.type = type;
        return this;
    }

    public void setType(AttendanceType type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AttendanceExcelUpload createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public AttendanceExcelUpload createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public AttendanceExcelUpload updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public AttendanceExcelUpload updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public AttendanceExcelUpload attendances(Set<Attendance> attendances) {
        this.attendances = attendances;
        return this;
    }

    public AttendanceExcelUpload addAttendance(Attendance attendance) {
        this.attendances.add(attendance);
        attendance.setAttendanceExcelUpload(this);
        return this;
    }

    public AttendanceExcelUpload removeAttendance(Attendance attendance) {
        this.attendances.remove(attendance);
        attendance.setAttendanceExcelUpload(null);
        return this;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
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
        AttendanceExcelUpload attendanceExcelUpload = (AttendanceExcelUpload) o;
        if (attendanceExcelUpload.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendanceExcelUpload.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendanceExcelUpload{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", type='" + getType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
