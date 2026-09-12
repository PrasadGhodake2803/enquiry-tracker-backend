package com.EnquriyTracker.EnquriyTracker.DTO;

public class DailyEnquiryCount {

    private String date;
    private Long count;

    public DailyEnquiryCount(String date, Long count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public Long getCount() {
        return count;
    }
}