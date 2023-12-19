package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Long> {
}
