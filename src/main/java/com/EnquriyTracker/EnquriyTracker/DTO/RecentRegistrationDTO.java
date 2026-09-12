package com.EnquriyTracker.EnquriyTracker.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecentRegistrationDTO {

    private Integer enquiryId;

    private String name;

    private String mobileNumber;

    private String parentmobileNumber;

    private String dob;

    private String email;

    private String residentialAddress;

    private String permanentAddress;

    private String educationDetails;

    private String courseName;

    private String batchTime;

    private String startDate;

    /*
     * ============================================================
     * CONSTRUCTOR FOR NATIVE SQL QUERY
     * ============================================================
     */

    public RecentRegistrationDTO(
            Integer enquiryId,
            String name,
            String mobileNumber,
            String parentmobileNumber,
            String dob,
            String email,
            String residentialAddress,
            String permanentAddress,
            String educationDetails,
            String courseName,
            String batchTime,
            String startDate) {

        this.enquiryId = enquiryId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.parentmobileNumber = parentmobileNumber;
        this.dob = dob;
        this.email = email;
        this.residentialAddress = residentialAddress;
        this.permanentAddress = permanentAddress;
        this.educationDetails = educationDetails;
        this.courseName = courseName;
        this.batchTime = batchTime;
        this.startDate = startDate;
    }

    /*
     * ============================================================
     * GETTERS / SETTERS
     * ============================================================
     */

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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}