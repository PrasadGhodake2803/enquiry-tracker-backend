package com.EnquriyTracker.EnquriyTracker.DTO;

public class CourseRegistrationDTO {

    private String course;
    private Long count;

    public CourseRegistrationDTO() {
    }

    public CourseRegistrationDTO(String course, Long count) {
        this.course = course;
        this.count = count;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}