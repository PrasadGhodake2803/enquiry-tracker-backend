package com.EnquriyTracker.EnquriyTracker.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String courseName;

    private String batchTime;

    private String courseStartDate;

    private String courseCode;

    private String courseDiscriptioin;

    private String courseFess;

    private String courseDuration;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;


    /*
     * ============================================================
     * REGISTRATIONS USING THIS COURSE
     * ============================================================
     */

    @OneToMany(
        mappedBy = "course",
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<RegistrationCourseEntity> registrations =
            new ArrayList<>();


    // ============================================================
    // GETTERS & SETTERS
    // ============================================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseDiscriptioin() {
        return courseDiscriptioin;
    }

    public void setCourseDiscriptioin(
            String courseDiscriptioin) {

        this.courseDiscriptioin =
                courseDiscriptioin;
    }

    public String getCourseFess() {
        return courseFess;
    }

    public void setCourseFess(String courseFess) {
        this.courseFess = courseFess;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public List<RegistrationCourseEntity> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(
            List<RegistrationCourseEntity> registrations) {

        this.registrations = registrations;
    }
}