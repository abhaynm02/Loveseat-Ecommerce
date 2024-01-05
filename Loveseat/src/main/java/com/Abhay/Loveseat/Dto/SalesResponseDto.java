package com.Abhay.Loveseat.Dto;

import com.Abhay.Loveseat.Enums.PaymentMethods;

import java.time.LocalDateTime;

public class SalesResponseDto {
    private long orderId;
    private String userId;
    private  String productName;
    private  long productQuantity;
    private  float totalPrice;
    private LocalDateTime orderDate;
    private PaymentMethods paymentMethods;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public String toString() {
        return "SalesResponseDto{" +
                "orderId=" + orderId +
                ", userId='" + userId + '\'' +
                ", productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", paymentMethods=" + paymentMethods +
                '}';
    }
}
