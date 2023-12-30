package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Long, Coupon> {
}
