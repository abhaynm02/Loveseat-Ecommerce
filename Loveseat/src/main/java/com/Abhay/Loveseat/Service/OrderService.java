package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Model.Orders;
import com.Abhay.Loveseat.Model.UserEntity;

public interface OrderService {
    Orders placeOrder(long addressId, UserEntity user);
}
