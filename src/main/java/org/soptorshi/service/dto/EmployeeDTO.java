package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.MaritalStatus;
import org.soptorshi.domain.enumeration.Gender;
import org.soptorshi.domain.enumeration.Religion;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.EmploymentType;

/**
 * A DTO for the Employee entity.
 */
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotNull
    private String employeeId;

    @NotNull
    private String fullName;

    @NotNull
    private String fathersName;

    @NotNull
    private String mothersName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    private Gender gender;

    @NotNull
    private Religion religion;

    @NotNull
    private String permanentAddress;

    @NotNull
    private String presentAddress;

    @NotNull
    private String nId;

    private String tin;

    @NotNull
    private String contactNumber;

    private String email;

    private String bloodGroup;

    @NotNull
    private String emergencyContact;

    private LocalDate joiningDate;

    private Long manager;

    private EmployeeStatus employeeStatus;

    private EmploymentType employmentType;

    private LocalDate terminationDate;

    private String reasonOfTermination;

    private Boolean userAccount;

    @Lob
    private byte[] photo;

    private String photoContentType;
    private BigDecimal hourlySalary;

    private String bankAccountNo;

    private String bankName;


    private Long departmentId;

    private String departmentName;

    private Long officeId;

    private String officeName;

    private Long designationId;

    private String designationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getReasonOfTermination() {
        return reasonOfTermination;
    }

    public void setReasonOfTermination(String reasonOfTermination) {
        this.reasonOfTermination = reasonOfTermination;
    }

    public Boolean isUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Boolean userAccount) {
        this.userAccount = userAccount;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public BigDecimal getHourlySalary() {
        return hourlySalary;
    }

    public void setHourlySalary(BigDecimal hourlySalary) {
        this.hourlySalary = hourlySalary;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", employeeId='" + getEmployeeId() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fathersName='" + getFathersName() + "'" +
            ", mothersName='" + getMothersName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", gender='" + getGender() + "'" +
            ", religion='" + getReligion() + "'" +
            ", permanentAddress='" + getPermanentAddress() + "'" +
            ", presentAddress='" + getPresentAddress() + "'" +
            ", nId='" + getnId() + "'" +
            ", tin='" + getTin() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", emergencyContact='" + getEmergencyContact() + "'" +
            ", joiningDate='" + getJoiningDate() + "'" +
            ", manager=" + getManager() +
            ", employeeStatus='" + getEmployeeStatus() + "'" +
            ", employmentType='" + getEmploymentType() + "'" +
            ", terminationDate='" + getTerminationDate() + "'" +
            ", reasonOfTermination='" + getReasonOfTermination() + "'" +
            ", userAccount='" + isUserAccount() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", hourlySalary=" + getHourlySalary() +
            ", bankAccountNo='" + getBankAccountNo() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            ", office=" + getOfficeId() +
            ", office='" + getOfficeName() + "'" +
            ", designation=" + getDesignationId() +
            ", designation='" + getDesignationName() + "'" +
            "}";
    }
}
