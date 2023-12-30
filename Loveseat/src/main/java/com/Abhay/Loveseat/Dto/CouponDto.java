package com.Abhay.Loveseat.Dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class CouponDto {
    private long id;
    @NotBlank(message ="please enter a valid coupon cod" )
    private String couponCod;
    @NotBlank(message = "please select valid date")
    private Date expartionDate;
    private String description;
    @NotBlank(message = "please enter a amount")
    private double discountAmount;
    private boolean disable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCouponCod() {
        return couponCod;
    }

    public void setCouponCod(String couponCod) {
        this.couponCod = couponCod;
    }

    public Date getExpartionDate() {
        return expartionDate;
    }

    public void setExpartionDate(Date expartionDate) {
        this.expartionDate = expartionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
