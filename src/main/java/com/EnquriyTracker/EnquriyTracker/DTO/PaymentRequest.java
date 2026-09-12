package com.EnquriyTracker.EnquriyTracker.DTO;

public class PaymentRequest {

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

    /*
     * This is the amount ENTERED by the user.
     *
     * Backend calculates GST and the gross amount actually applied
     * to the registration balance.
     */
    private double amountPaid;

    private double balanceAmount;

    private String paymentMode;
    private String paymentDate;
    private String transactionID;
    private String creditedAccount;
    private String other;

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

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
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
