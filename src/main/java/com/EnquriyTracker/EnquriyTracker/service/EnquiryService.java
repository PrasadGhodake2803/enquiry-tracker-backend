package com.EnquriyTracker.EnquriyTracker.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EnquriyTracker.EnquriyTracker.DTO.DailyEnquiryCount;
import com.EnquriyTracker.EnquriyTracker.entity.EnquiryEntity;
import com.EnquriyTracker.EnquriyTracker.repository.EnquiryRepository;

@Service
public class EnquiryService {

	
    @Autowired
    private EnquiryRepository repo;
    // Save Online Enquiry
	public String saveUser(EnquiryEntity enquiryEntity) {


        repo.save(enquiryEntity);

        return "Enquiry Submitted Successfully";
    }
    
    public List<DailyEnquiryCount> getLast7DaysChartData() {

        List<Object[]> result = repo.getLast7DaysEnquiryCounts();

        return result.stream()
                .map(row -> new DailyEnquiryCount(
                        row[0].toString(),
                        ((Number) row[1]).longValue()))
                .toList();
    }

}