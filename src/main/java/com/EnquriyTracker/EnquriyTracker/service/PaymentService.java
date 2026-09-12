
package com.EnquriyTracker.EnquriyTracker.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EnquriyTracker.EnquriyTracker.DTO.CollectionUpdateRequest;
import com.EnquriyTracker.EnquriyTracker.DTO.PaymentRequest;
import com.EnquriyTracker.EnquriyTracker.entity.EnquiryEntity;
import com.EnquriyTracker.EnquriyTracker.entity.PaymentEntity;
import com.EnquriyTracker.EnquriyTracker.entity.RegistrationEntity;
import com.EnquriyTracker.EnquriyTracker.repository.EnquiryRepository;
import com.EnquriyTracker.EnquriyTracker.repository.PaymentRepository;
import com.EnquriyTracker.EnquriyTracker.repository.RegisterRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RegisterRepository registrationRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;

    // ============================================================
    // SAVE PAYMENT
    // ============================================================

    @Transactional
    public PaymentEntity savePayment(PaymentRequest request) {

        // --------------------------------------------------------
        // 1. Validate request
        // --------------------------------------------------------

        if (request == null) {
            throw new RuntimeException("Payment request is required");
        }

        if (request.getRegistrationId() == null) {
            throw new RuntimeException("Registration ID is required");
        }

        Integer registrationId = request.getRegistrationId();

        // --------------------------------------------------------
        // 2. Find registration
        // --------------------------------------------------------

        RegistrationEntity registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() -> new RuntimeException(
                                "Registration not found with ID: "
                                        + registrationId
                        ));

        Integer enquiryId = registration.getEnquiry_id();

        // --------------------------------------------------------
        // 3. Base Amount
        // --------------------------------------------------------

        BigDecimal baseAmount =
                BigDecimal.valueOf(request.getBaseAmount())
                        .setScale(2, RoundingMode.HALF_UP);

        if (baseAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException(
                    "Base amount cannot be negative"
            );
        }

        // --------------------------------------------------------
        // 4. Discount
        // --------------------------------------------------------

        BigDecimal discount =
                BigDecimal.valueOf(request.getDiscount())
                        .setScale(2, RoundingMode.HALF_UP);

        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException(
                    "Discount cannot be negative"
            );
        }

        if (discount.compareTo(baseAmount) > 0) {
            throw new RuntimeException(
                    "Discount ₹"
                            + discount
                            + " cannot be greater than base amount ₹"
                            + baseAmount
            );
        }

        // --------------------------------------------------------
        // 5. Calculate Discounted Amount
        //
        // Discounted Amount =
        // Base Amount - Discount
        // --------------------------------------------------------

        BigDecimal discountedAmount =
                baseAmount
                        .subtract(discount)
                        .setScale(2, RoundingMode.HALF_UP);

        // --------------------------------------------------------
        // 6. GST Slab
        // --------------------------------------------------------

        BigDecimal gstRate =
                parseGstRate(request.getGstSlabs());

        // --------------------------------------------------------
        // 7. Calculate GST
        //
        // NEW LOGIC
        //
        // GST is ALWAYS calculated on discounted amount.
        //
        // GST Value =
        // Discounted Amount × GST % / 100
        // --------------------------------------------------------

        BigDecimal gstValue =
                discountedAmount
                        .multiply(gstRate)
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        );

        // --------------------------------------------------------
        // 8. Taxable Amount
        //
        // Taxable amount is the discounted amount.
        // --------------------------------------------------------

        BigDecimal taxableAmount =
                discountedAmount.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        // --------------------------------------------------------
        // 9. FINAL FEES
        //
        // NEW LOGIC
        //
        // Final Fees =
        // Discounted Amount + GST Value
        //
        // Inclusive / Exclusive no longer changes this.
        // --------------------------------------------------------

        BigDecimal finalFees =
                discountedAmount
                        .add(gstValue)
                        .setScale(2, RoundingMode.HALF_UP);

        // --------------------------------------------------------
        // 10. Current Payment
        //
        // IMPORTANT:
        // amountPaid contains ONLY the amount entered by user.
        // GST is NOT added to amountPaid.
        // --------------------------------------------------------

        BigDecimal currentPayment =
                BigDecimal.valueOf(request.getAmountPaid())
                        .setScale(2, RoundingMode.HALF_UP);

        if (currentPayment.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(
                    "Payment amount must be greater than zero"
            );
        }

        // --------------------------------------------------------
        // 11. Get Previous Total Paid
        // --------------------------------------------------------

        BigDecimal previousTotalPaid =
                paymentRepository.getTotalAmountPaid(
                        registrationId
                );

        if (previousTotalPaid == null) {
            previousTotalPaid = BigDecimal.ZERO;
        }

        previousTotalPaid =
                previousTotalPaid.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        if (previousTotalPaid.compareTo(BigDecimal.ZERO) < 0) {
            previousTotalPaid = BigDecimal.ZERO;
        }

        // --------------------------------------------------------
        // 12. Calculate Remaining Balance
        //
        // Remaining Balance =
        // Final Fees - Previous Total Paid
        // --------------------------------------------------------

        BigDecimal previousBalance =
                finalFees
                        .subtract(previousTotalPaid)
                        .setScale(2, RoundingMode.HALF_UP);

        if (previousBalance.compareTo(BigDecimal.ZERO) < 0) {
            previousBalance = BigDecimal.ZERO;
        }

        // --------------------------------------------------------
        // 13. Already Fully Paid
        // --------------------------------------------------------

        if (previousBalance.compareTo(BigDecimal.ZERO) == 0) {

            throw new RuntimeException(
                    "Registration fees are already fully paid"
            );
        }

        // --------------------------------------------------------
        // 14. Prevent Overpayment
        //
        // Current Payment MUST NOT exceed balance.
        // --------------------------------------------------------

        if (currentPayment.compareTo(previousBalance) > 0) {

            throw new RuntimeException(
                    "Payment amount ₹"
                            + currentPayment
                            + " cannot be greater than remaining balance ₹"
                            + previousBalance
            );
        }

        // --------------------------------------------------------
        // 15. Calculate Total Paid
        // --------------------------------------------------------

        BigDecimal totalPaid =
                previousTotalPaid
                        .add(currentPayment)
                        .setScale(2, RoundingMode.HALF_UP);

        // --------------------------------------------------------
        // 16. Calculate New Balance
        // --------------------------------------------------------

        BigDecimal balance =
                finalFees
                        .subtract(totalPaid)
                        .setScale(2, RoundingMode.HALF_UP);

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            balance = BigDecimal.ZERO;
        }

        // --------------------------------------------------------
        // 17. Create Payment Entity
        // --------------------------------------------------------

        PaymentEntity payment =
                new PaymentEntity();

        // Registration information
        payment.setRegistrationId(
                registration.getRegisterid()
        );

        payment.setEnquiryId(
                enquiryId
        );

        payment.setName(
                registration.getName()
        );

        payment.setMobileNumber(
                registration.getMobileNumber()
        );

        // --------------------------------------------------------
        // GST information
        // --------------------------------------------------------

        payment.setGstType(
                request.getGstType()
        );

        payment.setGstSlabs(
                request.getGstSlabs()
        );

        // --------------------------------------------------------
        // Amount information
        // --------------------------------------------------------

        payment.setBaseAmount(
                baseAmount.doubleValue()
        );

        payment.setDiscount(
                discount.doubleValue()
        );

        payment.setDiscountedAmount(
                discountedAmount.doubleValue()
        );

        // --------------------------------------------------------
        // GST calculated from discounted amount
        // --------------------------------------------------------

        payment.setGstValue(
                gstValue.doubleValue()
        );

        // --------------------------------------------------------
        // Taxable amount
        // --------------------------------------------------------

        payment.setTaxableAmount(
                taxableAmount.doubleValue()
        );

        // --------------------------------------------------------
        // FINAL FEES
        // --------------------------------------------------------

        payment.setFinalFees(
                finalFees.doubleValue()
        );

        // --------------------------------------------------------
        // IMPORTANT
        // --------------------------------------------------------

        payment.setAmountPaid(
                currentPayment.doubleValue()
        );

        // --------------------------------------------------------
        // Payment details
        // --------------------------------------------------------

        payment.setPaymentMode(
                request.getPaymentMode()
        );

        payment.setPaymentDate(
                request.getPaymentDate()
        );

        payment.setTransactionID(
                request.getTransactionID()
        );

        payment.setCreditedAccount(
                request.getCreditedAccount()
        );

        payment.setOther(
                request.getOther()
        );

        // --------------------------------------------------------
        // Current balance
        // --------------------------------------------------------

        payment.setBalanceAmount(
                balance.doubleValue()
        );

        // --------------------------------------------------------
        // 18. Save Payment
        // --------------------------------------------------------

        PaymentEntity savedPayment =
                paymentRepository.save(payment);

        // --------------------------------------------------------
        // 19. Update Registration
        // --------------------------------------------------------

        registration.setFinalFees(
                finalFees
        );

        registration.setAmountPaid(
                totalPaid
        );

        registration.setBalanceAmount(
                balance
        );

        registrationRepository.save(
                registration
        );

        // --------------------------------------------------------
        // 20. Update Enquiry Status
        // --------------------------------------------------------

        if (enquiryId != null) {

            EnquiryEntity enquiry =
                    enquiryRepository.findById(
                            enquiryId
                    ).orElse(null);

            if (enquiry != null) {

                if (balance.compareTo(BigDecimal.ZERO) == 0) {

                    enquiry.setStatus("PAID");

                } else {

                    enquiry.setStatus("REGISTERED");
                }

                enquiryRepository.save(
                        enquiry
                );
            }
        }

        // --------------------------------------------------------
        // 21. Return Saved Payment
        // --------------------------------------------------------

        return savedPayment;
    }

    // ============================================================
    // GST SLAB PARSER
    // ============================================================

    private BigDecimal parseGstRate(String slab) {

        if (slab == null || slab.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        String numeric =
                slab.trim()
                        .replace("%", "")
                        .trim();

        try {

            BigDecimal rate =
                    new BigDecimal(numeric);

            if (rate.compareTo(BigDecimal.ZERO) < 0) {

                throw new RuntimeException(
                        "GST slab cannot be negative"
                );
            }

            return rate;

        } catch (NumberFormatException ex) {

            throw new RuntimeException(
                    "Invalid GST slab: " + slab
            );
        }
    }
    
 // ============================================================
 // UPDATE COLLECTION
 // ============================================================
 
 @Transactional
 public PaymentEntity updateCollection(
         CollectionUpdateRequest request) {

     // ------------------------------------------------------------
     // 1. Validate request
     // ------------------------------------------------------------

     if (request == null) {
         throw new IllegalArgumentException(
                 "Collection request is required"
         );
     }

     if (request.getRegistrationId() == null) {
         throw new IllegalArgumentException(
                 "Registration ID is required"
         );
     }

     if (request.getAmountPaid() == null) {
         throw new IllegalArgumentException(
                 "Payment amount is required"
         );
     }

     Integer registrationId =
             request.getRegistrationId();

     // ------------------------------------------------------------
     // 2. Validate payment amount
     // ------------------------------------------------------------

     Double requestAmount =
             request.getAmountPaid();

     if (requestAmount.isNaN() ||
             requestAmount.isInfinite()) {

         throw new IllegalArgumentException(
                 "Invalid payment amount"
         );
     }

     BigDecimal currentPayment =
             BigDecimal.valueOf(requestAmount)
                     .setScale(
                             2,
                             RoundingMode.HALF_UP
                     );

     if (currentPayment.compareTo(
             BigDecimal.ZERO) <= 0) {

         throw new IllegalArgumentException(
                 "Payment amount must be greater than zero"
         );
     }

     // ------------------------------------------------------------
     // 3. Find registration
     // ------------------------------------------------------------

     RegistrationEntity registration =
             registrationRepository
                     .findById(registrationId)
                     .orElseThrow(() ->
                             new RuntimeException(
                                     "Registration not found with ID: "
                                             + registrationId
                             )
                     );

     // ------------------------------------------------------------
     // 4. FINAL FEES
     //
     // ------------------------------------------------------------

     BigDecimal finalFees =
             registration.getFinalFees();

     if (finalFees == null) {

         throw new RuntimeException(
                 "Final fees not found for registration ID: "
                         + registrationId
         );
     }

     finalFees =
             finalFees.setScale(
                     2,
                     RoundingMode.HALF_UP
             );

     if (finalFees.compareTo(
             BigDecimal.ZERO) < 0) {

         throw new RuntimeException(
                 "Final fees cannot be negative"
         );
     }

     // ------------------------------------------------------------
     // 5. GET PREVIOUSLY PAID
     //
     // RegistrationEntity is the source of truth.
     // ------------------------------------------------------------

     BigDecimal previousTotalPaid =
             registration.getAmountPaid();

     if (previousTotalPaid == null) {
         previousTotalPaid =
                 BigDecimal.ZERO;
     }

     previousTotalPaid =
             previousTotalPaid.setScale(
                     2,
                     RoundingMode.HALF_UP
             );

     if (previousTotalPaid.compareTo(
             BigDecimal.ZERO) < 0) {

         previousTotalPaid =
                 BigDecimal.ZERO;
     }

     // ------------------------------------------------------------
     // 6. Calculate current balance
     //
     // Balance =
     // Final Fees - Previously Paid
     // ------------------------------------------------------------

     BigDecimal currentBalance =
             finalFees
                     .subtract(previousTotalPaid)
                     .setScale(
                             2,
                             RoundingMode.HALF_UP
                     );

     if (currentBalance.compareTo(
             BigDecimal.ZERO) < 0) {

         currentBalance =
                 BigDecimal.ZERO;
     }

     // ------------------------------------------------------------
     // 7. Already fully paid
     // ------------------------------------------------------------

     if (currentBalance.compareTo(
             BigDecimal.ZERO) == 0) {

         throw new RuntimeException(
                 "Registration fees are already fully paid"
         );
     }

     // ------------------------------------------------------------
     // 8. Prevent overpayment
     // ------------------------------------------------------------

     if (currentPayment.compareTo(
             currentBalance) > 0) {

         throw new IllegalArgumentException(
                 "Payment amount ₹"
                         + currentPayment
                         + " cannot be greater than "
                         + "current balance ₹"
                         + currentBalance
         );
     }

     // ------------------------------------------------------------
     // 9. New total paid
     //
     // Previous Paid + New Collection
     // ------------------------------------------------------------

     BigDecimal newTotalPaid =
             previousTotalPaid
                     .add(currentPayment)
                     .setScale(
                             2,
                             RoundingMode.HALF_UP
                     );

     // ------------------------------------------------------------
     // 10. New balance
     //
     // Final Fees - New Total Paid
     // ------------------------------------------------------------

     BigDecimal newBalance =
             finalFees
                     .subtract(newTotalPaid)
                     .setScale(
                             2,
                             RoundingMode.HALF_UP
                     );

     if (newBalance.compareTo(
             BigDecimal.ZERO) < 0) {

         newBalance =
                 BigDecimal.ZERO;
     }

     // ------------------------------------------------------------
     // 11. Create payment transaction
     //
     // This PaymentEntity represents ONLY the new collection.
     // ------------------------------------------------------------

     PaymentEntity payment =
             new PaymentEntity();

     // ------------------------------------------------------------
     // Registration information
     // ------------------------------------------------------------

     payment.setRegistrationId(
             registration.getRegisterid()
     );

     payment.setEnquiryId(
             registration.getEnquiry_id()
     );

     payment.setName(
             registration.getName()
     );

     payment.setMobileNumber(
             registration.getMobileNumber()
     );

     // ------------------------------------------------------------
     // GST fields
     //
     // Update Collection does NOT calculate GST.
     //
     // Set neutral values because this is only
     // a collection transaction.
     // ------------------------------------------------------------

     payment.setGstType(
             null
     );

     payment.setGstSlabs(
             null
     );

     payment.setGstValue(
             0.0
     );

     // ------------------------------------------------------------
     // Financial information
     //
     // IMPORTANT:
     //
     // finalFees = original database final fees
     // amountPaid = THIS transaction only
     // balanceAmount = balance AFTER this transaction
     // ------------------------------------------------------------

     payment.setBaseAmount(
             finalFees.doubleValue()
     );

     payment.setDiscount(
             0.0
     );

     payment.setDiscountedAmount(
             finalFees.doubleValue()
     );

     payment.setTaxableAmount(
             currentPayment.doubleValue()
     );

     payment.setFinalFees(
             finalFees.doubleValue()
     );

     payment.setAmountPaid(
             currentPayment.doubleValue()
     );

     payment.setBalanceAmount(
             newBalance.doubleValue()
     );

     // ------------------------------------------------------------
     // Payment details
     // ------------------------------------------------------------

     payment.setPaymentMode(
             clean(request.getPaymentMode())
     );

     payment.setPaymentDate(
             clean(request.getPaymentDate())
     );

     payment.setTransactionID(
             clean(request.getTransactionID())
     );

     payment.setCreditedAccount(
             clean(request.getCreditedAccount())
     );

     payment.setOther(
             clean(request.getOther())
     );

     // ------------------------------------------------------------
     // 12. Save payment transaction
     // ------------------------------------------------------------

     PaymentEntity savedPayment =
             paymentRepository.save(payment);

     // ------------------------------------------------------------
     // 13. UPDATE REGISTRATION
     //
     // ONLY amountPaid and balanceAmount are changed.
     //
     // finalFees remains exactly as it was.
     // ------------------------------------------------------------

     registration.setAmountPaid(
             newTotalPaid
     );

     registration.setBalanceAmount(
             newBalance
     );

     registrationRepository.save(
             registration
     );

     // ------------------------------------------------------------
     // 14. Update enquiry status
     // ------------------------------------------------------------

     Integer enquiryId =
             registration.getEnquiry_id();

     if (enquiryId != null) {

         EnquiryEntity enquiry =
                 enquiryRepository
                         .findById(enquiryId)
                         .orElse(null);

         if (enquiry != null) {

             if (newBalance.compareTo(
                     BigDecimal.ZERO) == 0) {

                 enquiry.setStatus("PAID");

             } else {

                 enquiry.setStatus("REGISTERED");
             }

             enquiryRepository.save(enquiry);
         }
     }

     // ------------------------------------------------------------
     // 15. Return saved payment
     // ------------------------------------------------------------

     return savedPayment;
 }

 // ============================================================
 // STRING CLEANER
 // ============================================================

 private String clean(String value) {

     if (value == null) {
         return null;
     }

     String cleaned =
             value.trim();

     return cleaned.isEmpty()
             ? null
             : cleaned;
 }
}

