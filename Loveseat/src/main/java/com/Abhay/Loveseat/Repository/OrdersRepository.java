package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    @Query("SELECT o FROM Orders o WHERE o.user.id = :userId")
    Page<Orders> findOrders(@Param("userId") Long userId, Pageable pageable);

}
