package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
