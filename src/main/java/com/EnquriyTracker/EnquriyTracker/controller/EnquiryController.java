package com.EnquriyTracker.EnquriyTracker.controller;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EnquriyTracker.EnquriyTracker.DTO.DailyEnquiryCount;
import com.EnquriyTracker.EnquriyTracker.entity.EnquiryEntity;
import com.EnquriyTracker.EnquriyTracker.repository.EnquiryRepository;
import com.EnquriyTracker.EnquriyTracker.service.EnquiryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class EnquiryController {

    @Autowired
    private EnquiryService service;

    // Save Online Enquiry
    @PostMapping("/enquiry")
    public ResponseEntity<String> enquiryUser(	
            @RequestBody EnquiryEntity enquiryEntity) {

        enquiryEntity.setCreatedAt(LocalDateTime.now());

        String msg = service.saveUser(enquiryEntity);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Autowired
    private EnquiryRepository repo;
    
    @GetMapping("/student/find")
    public List searchStudent(
            @RequestParam String keyword) {

        return repo.findByNameStartingWithIgnoreCase(keyword)
                .stream()
                .map(EnquiryEntity::getName)
                .toList();
    }
    @GetMapping("/student/search")
    public ResponseEntity<List<EnquiryEntity>> searchStudents(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
            repo.searchStudents(keyword)
        );
    }
    @GetMapping("/student/details")
    public EnquiryEntity getStudentDetails(
            @RequestParam String name) {
    	
        return repo.findByName(name)
                .orElseThrow();
    }
 
    @GetMapping("/enquiry-chart-data")
    public List<DailyEnquiryCount> getChartData() {
        return service.getLast7DaysChartData();
    }
    
    @GetMapping ("/student/enquirydata")
    public List<EnquiryEntity> getEnquiryData(){
    	return repo.findAll();
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<EnquiryEntity>> getAllEnquiries() {
        return ResponseEntity.ok(repo.findAll());
    }
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<EnquiryEntity> updateEnquiry(
            @PathVariable Integer id,
            @RequestBody EnquiryEntity updatedEnquiry) {

        EnquiryEntity enquiry = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Enquiry not found"));

        enquiry.setName(updatedEnquiry.getName());
        enquiry.setMobileNumber(updatedEnquiry.getMobileNumber());
        enquiry.setEmail(updatedEnquiry.getEmail());
        enquiry.setCourse(updatedEnquiry.getCourse());
        enquiry.setEnquiryType(updatedEnquiry.getEnquiryType());

        enquiry.setFollowUp(updatedEnquiry.getFollowUp());
        enquiry.setRemarks(updatedEnquiry.getRemarks());
        enquiry.setStatus(updatedEnquiry.getStatus());
        repo.save(enquiry);

        return ResponseEntity.ok(enquiry);
    }
}