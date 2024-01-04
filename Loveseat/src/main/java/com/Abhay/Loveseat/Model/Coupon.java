package com.Abhay.Loveseat.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Coupon",uniqueConstraints = @UniqueConstraint( columnNames = "couponCod"))
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String couponCod;
    private LocalDate expatriationDate;
    private double discountAmount;
    private  double minimumAmount;
    private String description;
    private int stock;
    private boolean disable;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(double minimumAmount) {
        this.minimumAmount = minimumAmount;
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

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
