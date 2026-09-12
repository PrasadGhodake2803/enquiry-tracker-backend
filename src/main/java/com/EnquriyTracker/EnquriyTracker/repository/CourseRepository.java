package com.EnquriyTracker.EnquriyTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EnquriyTracker.EnquriyTracker.entity.CourseEntity;

public interface CourseRepository
        extends JpaRepository<CourseEntity, Integer> {

}