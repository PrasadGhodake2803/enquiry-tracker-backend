package com.EnquriyTracker.EnquriyTracker.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Registration_Table")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Registerid;

    private Integer Enquiry_id;

    private String name;

    private String mobileNumber;

    private String parentmobileNumber;

    private String dob;

    private String email;

    private String residentialAddress;

    private String permanentAddress;

    private String educationDetails;

    private String collegeName;

    private String specialization;

    /*
     * ============================================================
     * MULTIPLE COURSES
     * ============================================================
     */

    @OneToMany(
        mappedBy = "registration",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<RegistrationCourseEntity> courses =
            new ArrayList<>();

    
    @Transient
    @JsonProperty("courseName")
    public String getCourseName() {
    	if(courses== null ||courses.isEmpty()) {
    		return "";
    	}
    	return courses.stream().filter(course ->course !=null).map(RegistrationCourseEntity::getCourseName).filter(courseName -> 
    	courseName!= null && !courseName.trim().isEmpty()).distinct().collect(Collectors.joining(", "));
    }

    /*
     * ============================================================
     * FEES
     * ============================================================
     */

 
    private BigDecimal finalFees;


    /*
     * TOTAL amount paid across all payments.
     */
    private BigDecimal amountPaid;


    /*
     * Current remaining balance.
     */
    private BigDecimal balanceAmount;


    private String DateOfRegistration;


    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;


    /*
     * ============================================================
     * HELPER METHODS
     * ============================================================
     */

    public void addCourse(RegistrationCourseEntity course) {

        courses.add(course);

        course.setRegistration(this);
    }


    public void removeCourse(RegistrationCourseEntity course) {

        courses.remove(course);

        course.setRegistration(null);
    }


    /*
     * ============================================================
     * GETTERS / SETTERS
     * ============================================================
     */
    
  

    public Integer getRegisterid() {
        return Registerid;
    }

    public void setRegisterid(Integer registerid) {
        Registerid = registerid;
    }

    public Integer getEnquiry_id() {
        return Enquiry_id;
    }

    public void setEnquiry_id(Integer enquiry_id) {
        Enquiry_id = enquiry_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    
    public String getParentmobileNumber() {
        return parentmobileNumber;
    }

    public void setParentmobileNumber(String parentmobileNumber) {
        this.parentmobileNumber = parentmobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getEducationDetails() {
        return educationDetails;
    }

    public void setEducationDetails(String educationDetails) {
        this.educationDetails = educationDetails;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<RegistrationCourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<RegistrationCourseEntity> courses) {
        this.courses = courses;
    }

    public BigDecimal getFinalFees() {
        return finalFees;
    }

    public void setFinalFees(BigDecimal finalFees) {
        this.finalFees = finalFees;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getDateOfRegistration() {
        return DateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        DateOfRegistration = dateOfRegistration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}