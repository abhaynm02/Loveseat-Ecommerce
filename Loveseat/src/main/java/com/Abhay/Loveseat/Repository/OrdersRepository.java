package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
