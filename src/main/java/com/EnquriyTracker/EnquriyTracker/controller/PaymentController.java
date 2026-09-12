package com.EnquriyTracker.EnquriyTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EnquriyTracker.EnquriyTracker.DTO.CollectionUpdateRequest;
import com.EnquriyTracker.EnquriyTracker.DTO.PaymentRequest;
import com.EnquriyTracker.EnquriyTracker.entity.PaymentEntity;
import com.EnquriyTracker.EnquriyTracker.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ============================================================
    // INITIAL PAYMENT / REGISTRATION PAYMENT
    // ============================================================

    @PostMapping("/save")
    public ResponseEntity<?> savePayment(
            @RequestBody PaymentRequest request) {

        try {

            PaymentEntity savedPayment =
                    paymentService.savePayment(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedPayment);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Failed to save payment"
                    ));
        }
    }

    // ============================================================
    // UPDATE COLLECTION
    // ============================================================

    @PostMapping("/update-collection")
    public ResponseEntity<?> updateCollection(
            @RequestBody CollectionUpdateRequest request) {

        try {

            PaymentEntity savedPayment =
                    paymentService.updateCollection(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedPayment);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Unable to update collection"
                    ));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Internal server error"
                    ));
        }
    }

    // ============================================================
    // ERROR RESPONSE
    // ============================================================

    public static class ErrorResponse {

        private String message;

        public ErrorResponse() {
        }

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}