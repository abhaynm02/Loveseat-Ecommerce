package com.Abhay.Loveseat.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public class CouponDto {
    private long id;
    @NotBlank(message ="please enter a valid coupon cod" )
    private String couponCod;
    @NotNull(message = "Please select a valid date")
    private LocalDate expatriationDate;

    private  double minimumAmount;
    @NotBlank(message = "please enter description")
    private String description;
    @NotNull(message = "please enter a amount")
    private double discountAmount;
    private boolean disable;

    public long getId() {
        return id;
    }

    public double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(double minimumAmount) {
        this.minimumAmount = minimumAmount;
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

    public LocalDate getExpatriationDate() {
        return expatriationDate;
    }

    public void setExpatriationDate(LocalDate expatriationDate) {
        this.expatriationDate = expatriationDate;
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

    @Override
    public String toString() {
        return "CouponDto{" +
                "id=" + id +
                ", couponCod='" + couponCod + '\'' +
                ", expartionDate=" + expatriationDate +
                ", minimumAmount=" + minimumAmount +
                ", description='" + description + '\'' +
                ", discountAmount=" + discountAmount +
                ", disable=" + disable +
                '}';
    }
}
