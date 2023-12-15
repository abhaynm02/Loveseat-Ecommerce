package com.Abhay.Loveseat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonInput {
    private long productId;
    private int quantity;
    private  int addressId;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
