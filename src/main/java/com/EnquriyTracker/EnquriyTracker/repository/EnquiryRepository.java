package com.EnquriyTracker.EnquriyTracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EnquriyTracker.EnquriyTracker.entity.EnquiryEntity;

@Repository
public interface EnquiryRepository extends JpaRepository<EnquiryEntity, Integer> {

    // Total enquiries
    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.enquiry_table
            """, nativeQuery = true)
    int total();


    // Last 1 day
    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.enquiry_table
            WHERE date_of_enquiry >= CURDATE() - INTERVAL 1 DAY
            """, nativeQuery = true)
    int last1Day();


    // Last 7 days
    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.enquiry_table
            WHERE date_of_enquiry >= CURDATE() - INTERVAL 7 DAY
            """, nativeQuery = true)
    int last7Days();


    // Last 30 days
    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.enquiry_table
            WHERE date_of_enquiry >= CURDATE() - INTERVAL 30 DAY
            """, nativeQuery = true)
    int last30Days();


    // Online enquiries
    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.enquiry_table
            WHERE enquiry_type = 'Online Enquiry'
            """, nativeQuery = true)
    int onlineEnquiry();


    // Telephonic enquiries
    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.enquiry_table
            WHERE enquiry_type = 'Telephonic Enquiry'
            """, nativeQuery = true)
    int telephonicEnquiry();


    // Offline enquiries
    @Query(value = """
            SELECT COUNT(*)
            FROM enquiry_tracker.enquiry_table
            WHERE enquiry_type = 'Offline Enquiry'
            """, nativeQuery = true)
    int offlineEnquiry();


    // Last 7 days enquiry chart
    @Query(value = """
            SELECT DATE(date_of_enquiry) AS enquiry_date,
                   COUNT(*) AS total_count
            FROM enquiry_tracker.enquiry_table
            WHERE date_of_enquiry >= CURDATE() - INTERVAL 7 DAY
            GROUP BY DATE(date_of_enquiry)
            ORDER BY enquiry_date
            """, nativeQuery = true)
    List<Object[]> getLast7DaysEnquiryCounts();
    
    // finding name with name and enquiry id 
    @Query("""
            SELECT e
            FROM EnquiryEntity e
           WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))

        """)
        List<EnquiryEntity> searchStudents(
                @Param("keyword") String keyword
        );

    // Search by starting name
    List<EnquiryEntity> findByNameStartingWithIgnoreCase(String name);


    // Find enquiry by exact name
    Optional<EnquiryEntity> findByName(String name);




}