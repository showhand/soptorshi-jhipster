package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.MaritalStatus;

import org.soptorshi.domain.enumeration.Gender;

import org.soptorshi.domain.enumeration.Religion;

import org.soptorshi.domain.enumeration.EmployeeStatus;

import org.soptorshi.domain.enumeration.EmploymentType;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Document(indexName = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "fathers_name")
    private String fathersName;

    @Column(name = "mothers_name")
    private String mothersName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "age")
    private Double age;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private Religion religion;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "present_address")
    private String presentAddress;

    @Column(name = "n_id")
    private String nId;

    @Column(name = "tin")
    private String tin;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status")
    private EmployeeStatus employeeStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Column(name = "reason_of_termination")
    private String reasonOfTermination;

    @Column(name = "user_account")
    private Boolean userAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Designation designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Employee employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public Employee fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public Employee fathersName(String fathersName) {
        this.fathersName = fathersName;
        return this;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public Employee mothersName(String mothersName) {
        this.mothersName = mothersName;
        return this;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Employee birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getAge() {
        return age;
    }

    public Employee age(Double age) {
        this.age = age;
        return this;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Employee maritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Gender getGender() {
        return gender;
    }

    public Employee gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Religion getReligion() {
        return religion;
    }

    public Employee religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public Employee permanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
        return this;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public Employee presentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
        return this;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getnId() {
        return nId;
    }

    public Employee nId(String nId) {
        this.nId = nId;
        return this;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getTin() {
        return tin;
    }

    public Employee tin(String tin) {
        this.tin = tin;
        return this;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Employee contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public Employee bloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public Employee emergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
        return this;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public Employee employeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
        return this;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public Employee employmentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
        return this;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public Employee terminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
        return this;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getReasonOfTermination() {
        return reasonOfTermination;
    }

    public Employee reasonOfTermination(String reasonOfTermination) {
        this.reasonOfTermination = reasonOfTermination;
        return this;
    }

    public void setReasonOfTermination(String reasonOfTermination) {
        this.reasonOfTermination = reasonOfTermination;
    }

    public Boolean isUserAccount() {
        return userAccount;
    }

    public Employee userAccount(Boolean userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(Boolean userAccount) {
        this.userAccount = userAccount;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Employee designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeId='" + getEmployeeId() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fathersName='" + getFathersName() + "'" +
            ", mothersName='" + getMothersName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", age=" + getAge() +
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
            ", employeeStatus='" + getEmployeeStatus() + "'" +
            ", employmentType='" + getEmploymentType() + "'" +
            ", terminationDate='" + getTerminationDate() + "'" +
            ", reasonOfTermination='" + getReasonOfTermination() + "'" +
            ", userAccount='" + isUserAccount() + "'" +
            "}";
    }
}
