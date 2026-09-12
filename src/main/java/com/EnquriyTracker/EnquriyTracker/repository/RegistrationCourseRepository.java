package com.EnquriyTracker.EnquriyTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EnquriyTracker.EnquriyTracker.entity.RegistrationCourseEntity;

@Repository
public interface RegistrationCourseRepository
        extends JpaRepository<RegistrationCourseEntity, Integer> {

    /*
     * ============================================================
     * GET COURSES FOR A REGISTRATION
     * ============================================================
     *
     * Do NOT use:
     *
     * findByRegistration_Registerid(...)
     *
     * because Hibernate is resolving Registerid incorrectly.
     *
     * Explicit JPQL avoids Spring Data's property-name parser.
     */
    @Query("""
        SELECT rc
        FROM RegistrationCourseEntity rc
        WHERE rc.registration.Registerid = :registrationId
        """)
    List<RegistrationCourseEntity> findCoursesByRegistrationId(
            @Param("registrationId") Integer registrationId
    );


    /*
     * ============================================================
     * DELETE ALL COURSES FOR REGISTRATION
     * ============================================================
     */
    @Modifying
    @Query("""
        DELETE FROM RegistrationCourseEntity rc
        WHERE rc.registration.Registerid = :registrationId
        """)
    int deleteByRegistrationId(
            @Param("registrationId") Integer registrationId
    );
}