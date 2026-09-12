package com.EnquriyTracker.EnquriyTracker.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EnquriyTracker.EnquriyTracker.entity.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    List<PaymentEntity> findByRegistrationId(Integer registrationId);

    Optional<PaymentEntity> findTopByRegistrationIdOrderByPaymentIdDesc(
            Integer registrationId);

    @Query("""
            SELECT COALESCE(SUM(p.amountPaid), 0)
            FROM PaymentEntity p
            WHERE p.registrationId = :registrationId
            """)
    BigDecimal getTotalAmountPaid(
            @Param("registrationId") Integer registrationId);

    @Modifying
    @Query("""
            DELETE FROM PaymentEntity p
            WHERE p.registrationId = :registrationId
            """)
    int deleteByRegistrationId(
            @Param("registrationId") Integer registrationId);
}
