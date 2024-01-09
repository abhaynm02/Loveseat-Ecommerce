package com.Abhay.Loveseat.Dto;

import java.math.BigDecimal;

public class OrderChartResponse {
    private int year;
    private int month;
    private long orderCount;
    private BigDecimal totalAmount;

    // Constructor
    public OrderChartResponse(int year, int month, long orderCount, BigDecimal totalAmount) {
        this.year = year;
        this.month = month;
        this.orderCount = orderCount;
        this.totalAmount = totalAmount;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderChartResponse{" +
                "year=" + year +
                ", month=" + month +
                ", orderCount=" + orderCount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
