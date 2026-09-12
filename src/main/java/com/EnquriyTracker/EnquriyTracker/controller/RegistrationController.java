package com.EnquriyTracker.EnquriyTracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EnquriyTracker.EnquriyTracker.DTO.DailyRegistrationCount;
import com.EnquriyTracker.EnquriyTracker.DTO.RecentRegistrationDTO;
import com.EnquriyTracker.EnquriyTracker.DTO.RegistrationRequest;
import com.EnquriyTracker.EnquriyTracker.entity.RegistrationEntity;
import com.EnquriyTracker.EnquriyTracker.repository.RegisterRepository;
import com.EnquriyTracker.EnquriyTracker.service.Register_Service;

@RestController
@RequestMapping("/api/registerStudent")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private Register_Service service;

    @Autowired
    private RegisterRepository repo;

    @PostMapping("/create")
    public ResponseEntity<?> createRegistration(
            @RequestBody RegistrationRequest request) {
        try {
            RegistrationEntity saved = service.createRegistration(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage() != null
                                    ? e.getMessage()
                                    : "Unable to create registration"));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<RegistrationEntity> saveRegistration(
            @RequestBody RegistrationEntity entity) {
        return ResponseEntity.ok(repo.save(entity));
    }

    @GetMapping("/chart-data")
    public List<DailyRegistrationCount> getChartData() {
        return service.getLast7DaysRegistrationCounts();
    }

    @GetMapping("/latest")
    public List<RecentRegistrationDTO> getLatestStudents() {
        return service.RecentRegistrationDTO();
    }

    
    @GetMapping("/student/registrationdata")
    public ResponseEntity<List<Map<String, Object>>> getRegistrationData() {
        return ResponseEntity.ok(service.getRegistrationData());
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegistrationEntity>> getAllRegistrations() {
        return ResponseEntity.ok(repo.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRegistration(
            @PathVariable Integer id,
            @RequestBody RegistrationEntity updated) {
        try {
            RegistrationEntity register = repo.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Registration not found"));

            if (updated.getName() != null) {
                register.setName(updated.getName());
            }
            if (updated.getMobileNumber() != null) {
                register.setMobileNumber(updated.getMobileNumber());
            }
            if (updated.getEmail() != null) {
                register.setEmail(updated.getEmail());
            }
            if (updated.getPermanentAddress() != null) {
                register.setPermanentAddress(updated.getPermanentAddress());
            }
            if (updated.getResidentialAddress() != null) {
                register.setResidentialAddress(updated.getResidentialAddress());
            }
            if (updated.getDob() != null) {
                register.setDob(updated.getDob());
            }

            return ResponseEntity.ok(repo.save(register));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage() != null
                                    ? e.getMessage()
                                    : "Unable to update registration"));
        }
    }

    @DeleteMapping("/delete/{registrationId}")
    public ResponseEntity<?> deleteRegistration(
            @PathVariable Integer registrationId) {
        try {
            service.deleteRegistration(registrationId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Registration deleted successfully",
                    "registrationId", registrationId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage() != null
                                    ? e.getMessage()
                                    : "Unable to delete registration",
                            "registrationId", registrationId));
        }
    }
}
