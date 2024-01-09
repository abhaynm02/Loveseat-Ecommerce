package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Long> {
    @Query("SELECT o FROM OrderItem o WHERE o.orders.orderDate BETWEEN :startDate AND :endDate")
    List<OrderItem> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
