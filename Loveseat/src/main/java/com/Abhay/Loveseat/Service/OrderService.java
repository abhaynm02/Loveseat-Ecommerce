package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Model.OrderItem;
import com.Abhay.Loveseat.Model.Orders;
import com.Abhay.Loveseat.Model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {
    Orders placeOrder(long addressId, UserEntity user);

    Page<Orders> findOrders(Pageable pageable, UserEntity user);

    void cancelOrder(long orderId);

    Page<OrderItem> getAllOrders(Pageable pageable);

    Optional<OrderItem> findOrderItemById(long id);

    void updateOrder(long orderId, String status);
}
