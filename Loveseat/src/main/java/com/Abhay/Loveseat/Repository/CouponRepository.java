package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    @Modifying
    @Query("SELECT c FROM Coupon c WHERE c.disable = true AND CURRENT_DATE <= c.expatriationDate AND c.stock > 0")
    List<Coupon>findAllActiveCoupons();

}
