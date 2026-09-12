package com.EnquriyTracker.EnquriyTracker.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Enquiry_Table")

public class EnquiryEntity {
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer Enquiry_id;
	  	private String name;
	  	private String gender;
	    @JsonProperty("mobile_number")
	    private String mobileNumber;
	    @Column(name = "date_of_enquiry")
	    private LocalDate dateOfEnquiry;
	    private String email;
	    private String dob;
	    private String residencial_address;
	    private String education_details;
	    private String specialization;
	    private String collegeName;
	    private String course;
	
	    private String work_exp;
	    private String prefered_batch;
	    private String planning_to_start;
	    private String reference;
	    private String status; 
	    private String remarks; 
	    private String followUp;
	
	    @Column(name = "enquiry_type")
	    private String enquiryType;
	    @Column(name = "created_at")
	    @CreationTimestamp
	    private LocalDateTime createdAt;
	   
	  
	
		
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

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		public String getDob() {
			return dob;
		}
		public void setDob(String dob) {
			this.dob = dob;
		}

		public String getResidencial_address() {
			return residencial_address;
		}

		public void setResidencial_address(String residencial_address) {
			this.residencial_address = residencial_address;
		}

		public String getEducation_details() {
			return education_details;
		}

		public void setEducation_details(String education_details) {
			this.education_details = education_details;
		}

		public String getCourse() {
			return course;
		}

		public void setCourse(String course) {
			this.course = course;
		}

	
		public String getWork_exp() {
			return work_exp;
		}

		public void setWork_exp(String work_exp) {
			this.work_exp = work_exp;
		}

		public String getPrefered_batch() {
			return prefered_batch;
		}

		public void setPrefered_batch(String prefered_batch) {
			this.prefered_batch = prefered_batch;
		}

		public String getPlanning_to_start() {
			return planning_to_start;
		}

		public void setPlanning_to_start(String planning_to_start) {
			this.planning_to_start = planning_to_start;
		}

		public String getReference() {
			return reference;
		}

		public void setReference(String reference) {
			this.reference = reference;
		}

		
		public String getEnquiryType() {
			return enquiryType;
		}
		public void setEnquiryType(String enquiryType) {
			this.enquiryType = enquiryType;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		

		public LocalDate getDateOfEnquiry() {
			return dateOfEnquiry;
		}

		public void setDateOfEnquiry(LocalDate dateOfEnquiry) {
			this.dateOfEnquiry = dateOfEnquiry;
		}

		public String getSpecialization() {
			return specialization;
		}

		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}

		public String getCollegeName() {
			return collegeName;
		}

		public void setCollegeName(String collegeName) {
			this.collegeName = collegeName;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getFollowUp() {
			return followUp;
		}

		public void setFollowUp(String followUp) {
			this.followUp = followUp;
		}

		@Override
		public String toString() {
			return "EnquiryEntity [id=" + Enquiry_id + ", name=" + name + ", gender=" + gender + ", mobileNumber="
					+ mobileNumber + ", email=" + email + ", residencial_address=" + residencial_address
					+ ", education_details=" + education_details + ", course=" + course + ", area_of_interest="
					 + ", work_exp=" + work_exp + ", prefered_batch=" + prefered_batch
					+ ", planning_to_start=" + planning_to_start + ", reference=" + reference + ", enquiryType="
					+ enquiryType + ", createdAt=" + createdAt + "]";
		}

		

}
