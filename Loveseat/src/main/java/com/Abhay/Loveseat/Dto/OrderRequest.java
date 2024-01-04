package com.Abhay.Loveseat.Dto;

public class OrderRequest {
    private  long couponId;
    private  double amount;

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
