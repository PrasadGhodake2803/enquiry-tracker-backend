package com.EnquriyTracker.EnquriyTracker.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.EnquriyTracker.EnquriyTracker.entity.PaymentEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EnquriyTracker.EnquriyTracker.DTO.DailyRegistrationCount;
import com.EnquriyTracker.EnquriyTracker.DTO.RecentRegistrationDTO;
import com.EnquriyTracker.EnquriyTracker.DTO.RegistrationCourseRequest;
import com.EnquriyTracker.EnquriyTracker.DTO.RegistrationRequest;
import com.EnquriyTracker.EnquriyTracker.entity.CourseEntity;
import com.EnquriyTracker.EnquriyTracker.entity.EnquiryEntity;
import com.EnquriyTracker.EnquriyTracker.entity.RegistrationCourseEntity;
import com.EnquriyTracker.EnquriyTracker.entity.RegistrationEntity;
import com.EnquriyTracker.EnquriyTracker.repository.CourseRepository;
import com.EnquriyTracker.EnquriyTracker.repository.EnquiryRepository;
import com.EnquriyTracker.EnquriyTracker.repository.PaymentRepository;
import com.EnquriyTracker.EnquriyTracker.repository.RegisterRepository;
import com.EnquriyTracker.EnquriyTracker.repository.RegistrationCourseRepository;

@Service
public class Register_Service {

    @Autowired
    private RegisterRepository repo;

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private RegistrationCourseRepository registrationCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    // ============================================================
    // CREATE REGISTRATION
    // ============================================================

    @Transactional
    public RegistrationEntity createRegistration(
            RegistrationRequest request) {

        if (request == null) {
            throw new RuntimeException(
                    "Registration request is required");
        }

        if (request.getCourses() == null ||
                request.getCourses().isEmpty()) {

            throw new RuntimeException(
                    "Please select at least one course");
        }

        RegistrationEntity registration =
                new RegistrationEntity();

        registration.setEnquiry_id(
                request.getEnquiryId());

        registration.setName(
                request.getName());

        registration.setMobileNumber(
                request.getMobileNumber());

        registration.setParentmobileNumber(
                request.getParentmobileNumber());

        registration.setDob(
                request.getDob());

        registration.setEmail(
                request.getEmail());

        registration.setResidentialAddress(
                request.getResidentialAddress());

        registration.setPermanentAddress(
                request.getPermanentAddress());

        registration.setEducationDetails(
                request.getEducationDetails());

        registration.setCollegeName(
                request.getCollegeName());

        registration.setSpecialization(
                request.getSpecialization());

        registration.setDateOfRegistration(
                request.getDateOfRegistration());


        // ========================================================
        // INITIAL PAYMENT VALUES
        // ========================================================

        registration.setAmountPaid(
                BigDecimal.ZERO);

        registration.setBalanceAmount(
                BigDecimal.ZERO);


        // ========================================================
        // ADD COURSES
        // ========================================================

        BigDecimal totalCourseFees =
                BigDecimal.ZERO;

        for (RegistrationCourseRequest courseRequest
                : request.getCourses()) {

            if (courseRequest.getCourseId() == null) {
                throw new RuntimeException(
                        "Course ID is required");
            }

            CourseEntity course =
                    courseRepository
                            .findById(
                                    courseRequest.getCourseId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Course not found with ID: "
                                                    + courseRequest
                                                    .getCourseId()
                                    )
                            );

            RegistrationCourseEntity
                    registrationCourse =
                    new RegistrationCourseEntity();

            registrationCourse.setCourse(course);

            registrationCourse.setCourseName(
                    course.getCourseName());

            registrationCourse.setBatchTime(
                    course.getBatchTime());

            registrationCourse.setStartDate(
                    course.getCourseStartDate());

            registrationCourse.setCourseFees(
                    course.getCourseFess());

            registrationCourse.setCourseDuration(
                    course.getCourseDuration());

            registration.addCourse(
                    registrationCourse);


            // ====================================================
            // COURSE FEE
            // ====================================================

            try {

                if (course.getCourseFess() != null &&
                        !course.getCourseFess()
                                .trim()
                                .isEmpty()) {

                    BigDecimal courseFee =
                            new BigDecimal(
                                    course.getCourseFess()
                                            .trim());

                    totalCourseFees =
                            totalCourseFees.add(
                                    courseFee);
                }

            } catch (NumberFormatException e) {

                throw new RuntimeException(
                        "Invalid course fee for course: "
                                + course.getCourseName()
                                + " | Fee: "
                                + course.getCourseFess());
            }
        }


        // ========================================================
        // FINAL FEES
        // ========================================================

        registration.setFinalFees(
                totalCourseFees);

        registration.setBalanceAmount(
                totalCourseFees);


        // ========================================================
        // SAVE
        // ========================================================

        RegistrationEntity saved =
                repo.save(registration);


        // ========================================================
        // UPDATE ENQUIRY STATUS
        // ========================================================

        if (saved.getEnquiry_id() != null) {

            EnquiryEntity enquiry =
                    enquiryRepository
                            .findById(
                                    saved.getEnquiry_id())
                            .orElse(null);

            if (enquiry != null) {

                enquiry.setStatus(
                        "Registered");

                enquiryRepository.save(
                        enquiry);
            }
        }

        return saved;
    }


    // ============================================================
    // REGISTRATION MANAGEMENT DATA
    // ============================================================

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getRegistrationData() {

        List<RegistrationEntity> registrations = repo.findAll();

        List<Map<String, Object>> result = new java.util.ArrayList<>();

        for (RegistrationEntity registration : registrations) {

            Map<String, Object> row = new java.util.LinkedHashMap<>();

            Integer registrationId = registration.getRegisterid();
            Integer enquiryId = registration.getEnquiry_id();

            row.put("Registerid", registrationId);
            row.put("registerId", registrationId);
            row.put("registrationId", registrationId);

            row.put("Enquiry_id", enquiryId);
            row.put("enquiry_id", enquiryId);
            row.put("enquiryId", enquiryId);

            row.put("name", safe(registration.getName()));
            row.put("mobileNumber", safe(registration.getMobileNumber()));
            row.put("parentmobileNumber",
                    safe(registration.getParentmobileNumber()));
            row.put("dob", safe(registration.getDob()));
            row.put("email", safe(registration.getEmail()));
            row.put("residentialAddress",
                    safe(registration.getResidentialAddress()));
            row.put("permanentAddress",
                    safe(registration.getPermanentAddress()));
            row.put("educationDetails",
                    safe(registration.getEducationDetails()));

            // Gender belongs to the enquiry record.
            String gender = "";
            if (enquiryId != null) {
                EnquiryEntity enquiry =
                        enquiryRepository.findById(enquiryId).orElse(null);

                if (enquiry != null) {
                    gender = safe(enquiry.getGender());
                }
            }
            row.put("gender", gender);
            List<RegistrationCourseEntity> registrationCourses =
                    registrationCourseRepository
                            .findCoursesByRegistrationId(registrationId);
            // Multiple course snapshot.
            List<RegistrationCourseEntity> courses =
            		registrationCourseRepository
                    .findCoursesByRegistrationId(registrationId);

            String courseNames = courses.stream()
                    .map(RegistrationCourseEntity::getCourseName)
                    .filter(v -> v != null && !v.trim().isEmpty())
                    .distinct()
                    .collect(java.util.stream.Collectors.joining(", "));

            String batches = courses.stream()
                    .map(RegistrationCourseEntity::getBatchTime)
                    .filter(v -> v != null && !v.trim().isEmpty())
                    .distinct()
                    .collect(java.util.stream.Collectors.joining(", "));

            String startDates = courses.stream()
                    .map(RegistrationCourseEntity::getStartDate)
                    .filter(v -> v != null && !v.trim().isEmpty())
                    .distinct()
                    .collect(java.util.stream.Collectors.joining(", "));

            row.put("courseName", courseNames);
            row.put("batchTime", batches);
            row.put("startDate", startDates);

            // Registration-level fee totals.
            row.put("finalFees",
                    registration.getFinalFees() == null
                            ? BigDecimal.ZERO
                            : registration.getFinalFees());

            row.put("amountPaid",
                    registration.getAmountPaid() == null
                            ? BigDecimal.ZERO
                            : registration.getAmountPaid());

            row.put("balanceAmount",
                    registration.getBalanceAmount() == null
                            ? BigDecimal.ZERO
                            : registration.getBalanceAmount());

            // Latest payment supplies payment-specific fields.
            String paymentMode = "";
            String transactionID = "";
            String utrNumber = "";
            String paymentDate = "";
            String gstType = "";
            String gstSlabs = "";

            if (registrationId != null) {
                PaymentEntity latestPayment =
                        paymentRepository
                                .findTopByRegistrationIdOrderByPaymentIdDesc(
                                        registrationId)
                                .orElse(null);

                if (latestPayment != null) {
                    paymentMode = safe(latestPayment.getPaymentMode());
                    transactionID = safe(latestPayment.getTransactionID());
                    utrNumber = safe(latestPayment.getCreditedAccount());
                    paymentDate = safe(latestPayment.getPaymentDate());
                    gstType = safe(latestPayment.getGstType());
                    gstSlabs = safe(latestPayment.getGstSlabs());
                }
            }

            row.put("paymentMode", paymentMode);
            row.put("transactionID", transactionID);
            row.put("transactionId", transactionID);
            row.put("utrNumber", utrNumber);
            row.put("paymentDate", paymentDate);
            row.put("gstType", gstType);
            row.put("gstSlabs", gstSlabs);

            row.put("DateOfRegistration",
                    safe(registration.getDateOfRegistration()));
            row.put("dateOfRegistration",
                    safe(registration.getDateOfRegistration()));
            row.put("createdAt", registration.getCreatedAt());

            result.add(row);
        }

        result.sort((a, b) -> {
            Object aDate = a.get("createdAt");
            Object bDate = b.get("createdAt");

            if (aDate instanceof java.time.LocalDateTime
                    && bDate instanceof java.time.LocalDateTime) {
                return ((java.time.LocalDateTime) bDate)
                        .compareTo((java.time.LocalDateTime) aDate);
            }

            return Integer.compare(
                    toInt(b.get("Registerid")),
                    toInt(a.get("Registerid")));
        });

        return result;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private int toInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        try {
            return Integer.parseInt(
                    value == null ? "0" : value.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    // ============================================================
    // OLD METHOD
    // ============================================================

    @Transactional
    public String saveUser(
            RegistrationEntity registerEntity) {

        repo.save(registerEntity);

        return "User Registered Successfully";
    }


    // ============================================================
    // CHART DATA
    // ============================================================

    public List<DailyRegistrationCount>
    getLast7DaysRegistrationCounts() {

        return repo.getLast7DaysRegistrationCounts();
    }


    // ============================================================
    // RECENT STUDENTS
    // ============================================================

    public List<RecentRegistrationDTO>
    RecentRegistrationDTO() {

        return repo.getLastFiveStudents();
    }


    // ============================================================
    // DELETE / RESET REGISTRATION
    // ============================================================

    @Transactional
    public void deleteRegistration(
            Integer registrationId) {

        // --------------------------------------------------------
        // 1. VALIDATE ID
        // --------------------------------------------------------

        if (registrationId == null) {

            throw new RuntimeException(
                    "Registration ID is required");
        }


        // --------------------------------------------------------
        // 2. FIND REGISTRATION
        // --------------------------------------------------------

        RegistrationEntity registration =
                repo.findById(registrationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Registration not found with ID: "
                                                + registrationId));


        // --------------------------------------------------------
        // 3. SAVE ENQUIRY ID BEFORE DELETE
        // --------------------------------------------------------

        Integer enquiryId =
                registration.getEnquiry_id();


        // --------------------------------------------------------
        // 4. DELETE PAYMENTS FIRST
        //
        // This is VERY important if payment_table has a
        // foreign-key reference to registration_table.
        // --------------------------------------------------------

        int deletedPayments =
                paymentRepository
                        .deleteByRegistrationId(
                                registrationId);


        // --------------------------------------------------------
        // 5. DELETE REGISTRATION COURSES
        // --------------------------------------------------------

        int deletedCourses =
                registrationCourseRepository
                        .deleteByRegistrationId(
                                registrationId);


        // --------------------------------------------------------
        // 6. DELETE REGISTRATION
        // --------------------------------------------------------

        repo.delete(
                registration);

        repo.flush();


        // --------------------------------------------------------
        // 7. RESTORE ENQUIRY STATUS
        //
        // Since the registration has been removed, the enquiry
        // should no longer remain "Registered".
        // --------------------------------------------------------

        if (enquiryId != null) {

            EnquiryEntity enquiry =
                    enquiryRepository
                            .findById(
                                    enquiryId)
                            .orElse(null);

            if (enquiry != null) {

                enquiry.setStatus(
                        "NEW");

                enquiryRepository.save(
                        enquiry);
            }
        }


        // --------------------------------------------------------
        // 8. LOG RESULT
        // --------------------------------------------------------

        System.out.println(
                "==========================================");

        System.out.println(
                "REGISTRATION RESET SUCCESSFUL");

        System.out.println(
                "Registration ID : "
                        + registrationId);

        System.out.println(
                "Payments Deleted: "
                        + deletedPayments);

        System.out.println(
                "Courses Deleted : "
                        + deletedCourses);

        System.out.println(
                "Registration Deleted: YES");

        System.out.println(
                "Enquiry ID : "
                        + enquiryId);

        System.out.println(
                "Enquiry Status: NEW");

        System.out.println(
                "==========================================");
    }
}