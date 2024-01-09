package com.Abhay.Loveseat.Dto;

import com.Abhay.Loveseat.Enums.PaymentMethods;

public class PaymentSummaryDto {
    private PaymentMethods paymentMethods;
    private long totalCount;
    private double totalAmount;
    public PaymentSummaryDto(PaymentMethods paymentMethod,long totalCount,double totalAmount){
        this.paymentMethods=paymentMethod;
        this.totalCount=totalCount;
        this.totalAmount=totalAmount;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "PaymentSummaryDto{" +
                "paymentMethods=" + paymentMethods +
                ", totalCount=" + totalCount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
