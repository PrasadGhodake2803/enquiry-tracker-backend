package com.EnquriyTracker.EnquriyTracker.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EnquriyTracker.EnquriyTracker.entity.CourseEntity;
import com.EnquriyTracker.EnquriyTracker.repository.CourseRepository;
import com.EnquriyTracker.EnquriyTracker.service.CourseService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseRepository repo;

    @Autowired
    private CourseService service;

    @PostMapping("/course")
    public ResponseEntity<String> enquiryUser(
            @RequestBody CourseEntity courseEntity) {

        courseEntity.setCreatedAt(LocalDateTime.now());

        String msg = service.saveUser(courseEntity);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/course")
    public List<CourseEntity> getCourses() {

        return repo.findAll();
    }
}