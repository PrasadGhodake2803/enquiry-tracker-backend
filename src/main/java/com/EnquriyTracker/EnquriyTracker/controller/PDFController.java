package com.EnquriyTracker.EnquriyTracker.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EnquriyTracker.EnquriyTracker.entity.CourseEntity;
import com.EnquriyTracker.EnquriyTracker.entity.EnquiryEntity;
import com.EnquriyTracker.EnquriyTracker.entity.RegistrationEntity;
import com.EnquriyTracker.EnquriyTracker.repository.CourseRepository;
import com.EnquriyTracker.EnquriyTracker.repository.EnquiryRepository;
import com.EnquriyTracker.EnquriyTracker.repository.RegisterRepository;
import com.EnquriyTracker.EnquriyTracker.service.PDFService;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "*")
public class PDFController {

    @Autowired
    private EnquiryRepository enquiryRepository;
    @Autowired
    private PDFService pdfService;
    
    
    @Autowired 
    private RegisterRepository registerRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @GetMapping("/EnquiryPdfdownload")
    public ResponseEntity<byte[]> EnquiryPdfdownload() {

        List<EnquiryEntity> enquiries = enquiryRepository.findAll();

        byte[] pdfBytes = pdfService.generateEnquiryPdf(enquiries);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "Enquiry_Report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }
    @GetMapping("/RegistrationPdfdownload")
    public ResponseEntity<byte[]> RegistrationPdfdownload() {

        List<RegistrationEntity> registration = registerRepository.findAll();

        byte[] pdfBytes = pdfService.generateRegistrationPdf(registration);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "Enquiry_Report.pdf");

        return (ResponseEntity<byte[]>) ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }
    
    
    
    @GetMapping("/CoursesPdfdownload")
    public ResponseEntity<byte[]> CoursesPdfdownload() {

        List<CourseEntity> courses = courseRepository.findAll();

        byte[] pdfBytes = pdfService.generateCoursePdf(courses);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "Enquiry_Report.pdf");

        return (ResponseEntity<byte[]>) ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }
}