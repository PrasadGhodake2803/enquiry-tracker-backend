package com.EnquriyTracker.EnquriyTracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EnquriyTracker.EnquriyTracker.DTO.RecentRegistrationDTO;
import com.EnquriyTracker.EnquriyTracker.service.DashboardService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;


    /*
     * ============================================================
     * DASHBOARD SUMMARY
     * ============================================================
     */

    @GetMapping
    public Map<String, Object> getDashboard() {

        return service.getDashboardData();
    }


    /*
     * ============================================================
     * COURSE REGISTRATION CHART
     * ============================================================
    */

    
    
    @GetMapping("/course-chart")
    public List<Map<String, Object>> getCourseChart() {

        return service.getCourseChart();
    }




    @GetMapping("/recent-registrations")
    public List<RecentRegistrationDTO> getRecentRegistrations() {

        return service.getRecentRegistrations();
    }
}