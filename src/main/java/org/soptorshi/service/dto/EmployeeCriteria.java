package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MaritalStatus;
import org.soptorshi.domain.enumeration.Gender;
import org.soptorshi.domain.enumeration.Religion;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.EmploymentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Employee entity. This class is used in EmployeeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /employees?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable {
    /**
     * Class for filtering MaritalStatus
     */
    public static class MaritalStatusFilter extends Filter<MaritalStatus> {
    }
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {
    }
    /**
     * Class for filtering Religion
     */
    public static class ReligionFilter extends Filter<Religion> {
    }
    /**
     * Class for filtering EmployeeStatus
     */
    public static class EmployeeStatusFilter extends Filter<EmployeeStatus> {
    }
    /**
     * Class for filtering EmploymentType
     */
    public static class EmploymentTypeFilter extends Filter<EmploymentType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter employeeId;

    private StringFilter fullName;

    private StringFilter fathersName;

    private StringFilter mothersName;

    private LocalDateFilter birthDate;

    private MaritalStatusFilter maritalStatus;

    private GenderFilter gender;

    private ReligionFilter religion;

    private StringFilter permanentAddress;

    private StringFilter presentAddress;

    private StringFilter nId;

    private StringFilter tin;

    private StringFilter contactNumber;

    private StringFilter email;

    private StringFilter bloodGroup;

    private StringFilter emergencyContact;

    private LocalDateFilter joiningDate;

    private LongFilter manager;

    private EmployeeStatusFilter employeeStatus;

    private EmploymentTypeFilter employmentType;

    private LocalDateFilter terminationDate;

    private StringFilter reasonOfTermination;

    private BooleanFilter userAccount;

    private BigDecimalFilter hourlySalary;

    private StringFilter bankAccountNo;

    private StringFilter bankName;

    private LongFilter departmentId;

    private LongFilter officeId;

    private LongFilter designationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(StringFilter employeeId) {
        this.employeeId = employeeId;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getFathersName() {
        return fathersName;
    }

    public void setFathersName(StringFilter fathersName) {
        this.fathersName = fathersName;
    }

    public StringFilter getMothersName() {
        return mothersName;
    }

    public void setMothersName(StringFilter mothersName) {
        this.mothersName = mothersName;
    }

    public LocalDateFilter getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateFilter birthDate) {
        this.birthDate = birthDate;
    }

    public MaritalStatusFilter getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public ReligionFilter getReligion() {
        return religion;
    }

    public void setReligion(ReligionFilter religion) {
        this.religion = religion;
    }

    public StringFilter getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(StringFilter permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public StringFilter getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(StringFilter presentAddress) {
        this.presentAddress = presentAddress;
    }

    public StringFilter getnId() {
        return nId;
    }

    public void setnId(StringFilter nId) {
        this.nId = nId;
    }

    public StringFilter getTin() {
        return tin;
    }

    public void setTin(StringFilter tin) {
        this.tin = tin;
    }

    public StringFilter getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(StringFilter contactNumber) {
        this.contactNumber = contactNumber;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(StringFilter bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public StringFilter getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(StringFilter emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public LocalDateFilter getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDateFilter joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LongFilter getManager() {
        return manager;
    }

    public void setManager(LongFilter manager) {
        this.manager = manager;
    }

    public EmployeeStatusFilter getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatusFilter employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public EmploymentTypeFilter getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentTypeFilter employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDateFilter getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDateFilter terminationDate) {
        this.terminationDate = terminationDate;
    }

    public StringFilter getReasonOfTermination() {
        return reasonOfTermination;
    }

    public void setReasonOfTermination(StringFilter reasonOfTermination) {
        this.reasonOfTermination = reasonOfTermination;
    }

    public BooleanFilter getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(BooleanFilter userAccount) {
        this.userAccount = userAccount;
    }

    public BigDecimalFilter getHourlySalary() {
        return hourlySalary;
    }

    public void setHourlySalary(BigDecimalFilter hourlySalary) {
        this.hourlySalary = hourlySalary;
    }

    public StringFilter getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(StringFilter bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getOfficeId() {
        return officeId;
    }

    public void setOfficeId(LongFilter officeId) {
        this.officeId = officeId;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(fathersName, that.fathersName) &&
            Objects.equals(mothersName, that.mothersName) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(permanentAddress, that.permanentAddress) &&
            Objects.equals(presentAddress, that.presentAddress) &&
            Objects.equals(nId, that.nId) &&
            Objects.equals(tin, that.tin) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(email, that.email) &&
            Objects.equals(bloodGroup, that.bloodGroup) &&
            Objects.equals(emergencyContact, that.emergencyContact) &&
            Objects.equals(joiningDate, that.joiningDate) &&
            Objects.equals(manager, that.manager) &&
            Objects.equals(employeeStatus, that.employeeStatus) &&
            Objects.equals(employmentType, that.employmentType) &&
            Objects.equals(terminationDate, that.terminationDate) &&
            Objects.equals(reasonOfTermination, that.reasonOfTermination) &&
            Objects.equals(userAccount, that.userAccount) &&
            Objects.equals(hourlySalary, that.hourlySalary) &&
            Objects.equals(bankAccountNo, that.bankAccountNo) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(officeId, that.officeId) &&
            Objects.equals(designationId, that.designationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        employeeId,
        fullName,
        fathersName,
        mothersName,
        birthDate,
        maritalStatus,
        gender,
        religion,
        permanentAddress,
        presentAddress,
        nId,
        tin,
        contactNumber,
        email,
        bloodGroup,
        emergencyContact,
        joiningDate,
        manager,
        employeeStatus,
        employmentType,
        terminationDate,
        reasonOfTermination,
        userAccount,
        hourlySalary,
        bankAccountNo,
        bankName,
        departmentId,
        officeId,
        designationId
        );
    }

    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (fathersName != null ? "fathersName=" + fathersName + ", " : "") +
                (mothersName != null ? "mothersName=" + mothersName + ", " : "") +
                (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
                (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (religion != null ? "religion=" + religion + ", " : "") +
                (permanentAddress != null ? "permanentAddress=" + permanentAddress + ", " : "") +
                (presentAddress != null ? "presentAddress=" + presentAddress + ", " : "") +
                (nId != null ? "nId=" + nId + ", " : "") +
                (tin != null ? "tin=" + tin + ", " : "") +
                (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (bloodGroup != null ? "bloodGroup=" + bloodGroup + ", " : "") +
                (emergencyContact != null ? "emergencyContact=" + emergencyContact + ", " : "") +
                (joiningDate != null ? "joiningDate=" + joiningDate + ", " : "") +
                (manager != null ? "manager=" + manager + ", " : "") +
                (employeeStatus != null ? "employeeStatus=" + employeeStatus + ", " : "") +
                (employmentType != null ? "employmentType=" + employmentType + ", " : "") +
                (terminationDate != null ? "terminationDate=" + terminationDate + ", " : "") +
                (reasonOfTermination != null ? "reasonOfTermination=" + reasonOfTermination + ", " : "") +
                (userAccount != null ? "userAccount=" + userAccount + ", " : "") +
                (hourlySalary != null ? "hourlySalary=" + hourlySalary + ", " : "") +
                (bankAccountNo != null ? "bankAccountNo=" + bankAccountNo + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (officeId != null ? "officeId=" + officeId + ", " : "") +
                (designationId != null ? "designationId=" + designationId + ", " : "") +
            "}";
    }

}
