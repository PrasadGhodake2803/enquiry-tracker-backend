package com.EnquriyTracker.EnquriyTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EnquriyTracker.EnquriyTracker.entity.CourseEntity;
import com.EnquriyTracker.EnquriyTracker.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repo;

    // Save Course
    public String saveUser(CourseEntity courseEntity) {

        repo.save(courseEntity);

        return "Course Added Successfully";
    }
    
    
}