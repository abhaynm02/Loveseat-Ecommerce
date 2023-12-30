package com.Abhay.Loveseat.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String couponCod;
    private Date expartionDate;
    private double discountAmount;
    private String description;
    private boolean disable;

    public long getId() {
        return id;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
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

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
