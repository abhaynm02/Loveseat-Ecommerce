package com.Abhay.Loveseat.Dto;

public class OrderResponse {
    private  Integer amount;
    private  String orderId;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "amount=" + amount +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
