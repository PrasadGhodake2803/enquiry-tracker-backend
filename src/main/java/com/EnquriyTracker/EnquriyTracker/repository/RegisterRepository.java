package com.EnquriyTracker.EnquriyTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.EnquriyTracker.EnquriyTracker.DTO.DailyRegistrationCount;
import com.EnquriyTracker.EnquriyTracker.DTO.RecentRegistrationDTO;
import com.EnquriyTracker.EnquriyTracker.entity.RegistrationEntity;

@Repository
public interface RegisterRepository
        extends JpaRepository<RegistrationEntity, Integer> {

    /*
     * ============================================================
     * TOTAL REGISTRATIONS
     * ============================================================
     */

    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.registration_table
            """, nativeQuery = true)
    int total();


    /*
     * ============================================================
     * LAST 1 DAY
     * ============================================================
     */

    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.registration_table
            WHERE date_of_registration >= NOW() - INTERVAL 1 DAY
            """, nativeQuery = true)
    int last1Day();


    /*
     * ============================================================
     * LAST 7 DAYS
     * ============================================================
     */

    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.registration_table
            WHERE date_of_registration >= NOW() - INTERVAL 7 DAY
            """, nativeQuery = true)
    int last7Days();


    /*
     * ============================================================
     * LAST 30 DAYS
     * ============================================================
     */

    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.registration_table
            WHERE date_of_registration >= NOW() - INTERVAL 30 DAY
            """, nativeQuery = true)
    int last30Days();


    /*
     * ============================================================
     * LAST 7 DAYS REGISTRATION CHART
     * ============================================================
     */

    @Query(value = """
            SELECT
                DATE(date_of_registration) AS date,
                COUNT(*) AS count
            FROM enquiry_tracker.registration_table
            WHERE date_of_registration >= CURDATE() - INTERVAL 7 DAY
            GROUP BY DATE(date_of_registration)
            ORDER BY DATE(date_of_registration)
            """, nativeQuery = true)
    List<DailyRegistrationCount> getLast7DaysRegistrationCounts();


    /*
     * ============================================================
     * COURSE REGISTRATION CHART
     * ============================================================
     *
     * IMPORTANT:
     *
     * Courses are stored in registration_course.
     *
     * Therefore, this query counts each course registration.
     *
     * Example:
     *
     * Student A -> Java + Python
     * Student B -> Java
     *
     * Result:
     *
     * Java   -> 2
     * Python -> 1
     *
     * This works correctly with multiple courses.
     */

    @Query(value = """
            SELECT
                COALESCE(course_name, 'Unknown') AS courseName,
                COUNT(*) AS total
            FROM enquiry_tracker.registration_course
            GROUP BY COALESCE(course_name, 'Unknown')
            ORDER BY total DESC
            """, nativeQuery = true)
    List<Object[]> getCourseRegistrationCount();


    /*
     * ============================================================
     * LAST 5 REGISTERED STUDENTS
     * ============================================================
     *
     * Student details:
     *      registration_table
     *
     * Course details:
     *      registration_course
     *
     * Multiple courses are combined using GROUP_CONCAT.
     *
     * Example:
     *
     * Student:
     *      Prasad
     *
     * Courses:
     *      Java, Python, Spring Boot
     *
     * Batch:
     *      Morning, Evening
     *
     * Start dates:
     *      2026-09-01, 2026-09-05, 2026-09-10
     *
     * Only one student row is returned.
     */

    @Query(value = """
            SELECT
                r.Enquiry_id AS enquiryId,

                r.name AS name,

                r.mobile_number AS mobileNumber,

                r.parentmobile_number AS parentmobileNumber,

                r.dob AS dob,

                r.email AS email,

                r.residential_address AS residentialAddress,

                r.permanent_address AS permanentAddress,

                r.education_details AS educationDetails,

                COALESCE(
                    GROUP_CONCAT(
                        DISTINCT rc.course_name
                        ORDER BY rc.id
                        SEPARATOR ', '
                    ),
                    ''
                ) AS courseName,

                COALESCE(
                    GROUP_CONCAT(
                        DISTINCT rc.batch_time
                        ORDER BY rc.id
                        SEPARATOR ', '
                    ),
                    ''
                ) AS batchTime,

                COALESCE(
                    GROUP_CONCAT(
                        DISTINCT rc.start_date
                        ORDER BY rc.id
                        SEPARATOR ', '
                    ),
                    ''
                ) AS startDate

            FROM enquiry_tracker.registration_table r

            LEFT JOIN enquiry_tracker.registration_course rc
                ON rc.registration_id = r.Registerid

            GROUP BY
                r.Registerid,
                r.Enquiry_id,
                r.name,
                r.mobile_number,
                r.parentmobile_number,
                r.dob,
                r.email,
                r.residential_address,
                r.permanent_address,
                r.education_details

            ORDER BY MAX(r.date_of_registration) DESC

            LIMIT 5
            """, nativeQuery = true)
    List<RecentRegistrationDTO> getLastFiveStudents();

}