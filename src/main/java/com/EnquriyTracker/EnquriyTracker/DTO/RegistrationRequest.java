package com.EnquriyTracker.EnquriyTracker.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private Integer enquiryId;

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
     * MULTIPLE COURSES
     */
    private List<RegistrationCourseRequest> courses;

    private String DateOfRegistration;

    /*
     * Optional.
     *
     * Backend should calculate this from the
     * selected courses instead of trusting the
     * frontend.
     */
    private java.math.BigDecimal finalFees;

	public Integer getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(Integer enquiryId) {
		this.enquiryId = enquiryId;
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

	public List<RegistrationCourseRequest> getCourses() {
		return courses;
	}

	public void setCourses(List<RegistrationCourseRequest> courses) {
		this.courses = courses;
	}

	public String getDateOfRegistration() {
		return DateOfRegistration;
	}

	public void setDateOfRegistration(String dateOfRegistration) {
		DateOfRegistration = dateOfRegistration;
	}

	public java.math.BigDecimal getFinalFees() {
		return finalFees;
	}

	public void setFinalFees(java.math.BigDecimal finalFees) {
		this.finalFees = finalFees;
	}
    
    
}