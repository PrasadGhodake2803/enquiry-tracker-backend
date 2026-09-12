package com.EnquriyTracker.EnquriyTracker.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EnquriyTracker.EnquriyTracker.DTO.CourseRegistrationDTO;
import com.EnquriyTracker.EnquriyTracker.DTO.RecentRegistrationDTO;
import com.EnquriyTracker.EnquriyTracker.repository.EnquiryRepository;
import com.EnquriyTracker.EnquriyTracker.repository.RegisterRepository;

@Service
public class DashboardService {

    @Autowired
    private EnquiryRepository enquiryRepo;

    @Autowired
    private RegisterRepository regRepo;
    
    
    
    public List<Map<String, Object>> getCourseChart() {

        List<Object[]> results = regRepo.getCourseRegistrationCount();

        List<Map<String, Object>> chartData = new ArrayList<>();

        for (Object[] obj : results) {

            String courseName =
                    obj[0] == null ? "Unknown" : obj[0].toString();

            long count = 0;

            if (obj[1] != null) {
                count = ((Number) obj[1]).longValue();
            }

            Map<String, Object> data = new HashMap<>();
            data.put("courseName", courseName);
            data.put("count", count);

            chartData.add(data);
        }

        return chartData;
    }

    
    public Map<String, Object> getDashboardData() {

        Map<String, Object> data = new HashMap<>();

        data.put("enquiryTotal", enquiryRepo.total());
        data.put("enquiry1Day", enquiryRepo.last1Day());
        data.put("enquiry7Day", enquiryRepo.last7Days());
        data.put("enquiry30Day", enquiryRepo.last30Days());
       
        data.put("regTotal", regRepo.total());
        data.put("reg1Day", regRepo.last1Day());
        data.put("reg7Day", regRepo.last7Days());
        data.put("reg30Day", regRepo.last30Days());


      
        
        data.put("onlineEnquiry", enquiryRepo.onlineEnquiry());

        data.put("offlineEnquiry", enquiryRepo.onlineEnquiry());
        
        data.put("telephonicEnquiry", enquiryRepo.onlineEnquiry());

        return data;
    }
    
    public List<RecentRegistrationDTO> getRecentRegistrations() {

        List<RecentRegistrationDTO> students =
                regRepo.getLastFiveStudents();

        if (students == null) {
            return new ArrayList<>();
        }

        return students;
    }
}






