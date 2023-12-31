package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.CouponDto;
import com.Abhay.Loveseat.Model.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CouponService {
    void saveCoupon(CouponDto couponDto);
    Page<Coupon>findAll(Pageable pageable);

    void unListCoupon(long couponId);

    void listCoupon(long couponId);

    Optional<Coupon> findById(long couponId);

    void saveEditCoupon(CouponDto couponDto);
}
