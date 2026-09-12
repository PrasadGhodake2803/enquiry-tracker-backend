package com.EnquriyTracker.EnquriyTracker.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registration_course")
public class RegistrationCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /*
     * ============================================================
     * REGISTRATION
     * ============================================================
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "registration_id",
        nullable = false
    )
    @JsonIgnore
    private RegistrationEntity registration;


    /*
     * ============================================================
     * COURSE
     * ============================================================
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "course_id",
        nullable = false
    )
    private CourseEntity course;


    /*
     * ============================================================
     * SNAPSHOT COURSE INFORMATION
     * ============================================================
     *
     * We keep these values because course information may change
     * later in the Course table.
     *
     * Example:
     *
     * Student registered for Java at ₹30,000.
     *
     * Later CourseEntity fee becomes ₹35,000.
     *
     * RegistrationCourseEntity still remembers ₹30,000.
     */

    private String courseName;

    private String batchTime;

    private String startDate;

    private String courseFees;

    private String courseDuration;


    /*
     * ============================================================
     * GETTERS / SETTERS
     * ============================================================
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RegistrationEntity getRegistration() {
        return registration;
    }

    public void setRegistration(
            RegistrationEntity registration) {

        this.registration = registration;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
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

    public String getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(String courseFees) {
        this.courseFees = courseFees;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }
}