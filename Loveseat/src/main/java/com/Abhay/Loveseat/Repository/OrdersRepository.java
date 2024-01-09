package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Dto.OrderChartResponse;
import com.Abhay.Loveseat.Model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    @Query("SELECT o FROM Orders o WHERE o.user.id = :userId")
    Page<Orders> findOrders(@Param("userId") Long userId, Pageable pageable);


    @Query("SELECT COUNT(o) " +
            "FROM Orders o " +
            "WHERE FUNCTION('YEAR', o.orderDate) = FUNCTION('YEAR', :date) " +
            "AND FUNCTION('MONTH', o.orderDate) = FUNCTION('MONTH', :date)")
    long countOrdersByMonth(@Param("date") LocalDateTime date);

    @Query("SELECT o.paymentMethods, COUNT(o), SUM(o.totalAmount) " +
            "FROM Orders o " +
            "WHERE o.id IN (SELECT oi.orders.id FROM OrderItem oi) " +
            "GROUP BY o.paymentMethods")
    List<Object[]> getPaymentSummary();

}
