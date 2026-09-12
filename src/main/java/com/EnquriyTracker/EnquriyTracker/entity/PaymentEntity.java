package com.EnquriyTracker.EnquriyTracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Payment_Table")
@Entity
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    private Integer registrationId;

    private Integer enquiryId;

    private String name;

    private String mobileNumber;

    private String gstType;

    private String gstSlabs;

    private double baseAmount;

    private double discount;

    private double discountedAmount;

    private double gstValue;

    private double taxableAmount;

    private double finalFees;

    private String paymentMode;

    /*
     * IMPORTANT:
     * This is ONLY the amount paid in THIS payment.
     *
     * Example:
     *
     * Payment 1 = 1000
     * Payment 2 = 2000
     *
     * Database:
     * Payment 1 -> amountPaid = 1000
     * Payment 2 -> amountPaid = 2000
     */
    private double amountPaid;

    /*
     * Balance AFTER this payment.
     */
    private double balanceAmount;

    private String paymentDate;

    private String transactionID;

    private String creditedAccount;

    private String other;


    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }


    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }


    public Integer getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Integer enquiryId) {
        this.enquiryId = enquiryId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }


    public String getGstSlabs() {
        return gstSlabs;
    }

    public void setGstSlabs(String gstSlabs) {
        this.gstSlabs = gstSlabs;
    }


    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public double getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }


    public double getGstValue() {
        return gstValue;
    }

    public void setGstValue(double gstValue) {
        this.gstValue = gstValue;
    }


    public double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }


    public double getFinalFees() {
        return finalFees;
    }

    public void setFinalFees(double finalFees) {
        this.finalFees = finalFees;
    }


    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }


    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }


    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }


    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }


    public String getCreditedAccount() {
        return creditedAccount;
    }

    public void setCreditedAccount(String creditedAccount) {
        this.creditedAccount = creditedAccount;
    }


    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}