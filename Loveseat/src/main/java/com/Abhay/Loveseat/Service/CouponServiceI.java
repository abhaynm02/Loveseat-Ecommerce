package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.CouponDto;
import com.Abhay.Loveseat.Model.Coupon;
import com.Abhay.Loveseat.Repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponServiceI implements CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Override
    public void saveCoupon(CouponDto couponDto) {
        Coupon coupon=new Coupon();
        coupon.setCouponCod(couponDto.getCouponCod().toUpperCase());
        coupon.setExpatriationDate(couponDto.getExpatriationDate());
        coupon.setMinimumAmount(couponDto.getMinimumAmount());
        coupon.setDescription(couponDto.getDescription());
        coupon.setDisable(true);
        coupon.setDiscountAmount(couponDto.getDiscountAmount());
        couponRepository.save(coupon);
    }

    @Override
    public Page<Coupon> findAll(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

    @Override
    public void unListCoupon(long couponId) {
        Optional<Coupon> coupon=couponRepository.findById(couponId);
        Coupon coupon1=coupon.get();
        coupon1.setDisable(false);
        couponRepository.save(coupon1);
    }

    @Override
    public void listCoupon(long couponId) {
        Optional<Coupon>couponOptional=couponRepository.findById(couponId);
        Coupon coupon=couponOptional.get();
        coupon.setDisable(true);
        couponRepository.save(coupon);
    }

    @Override
    public Optional<Coupon> findById(long couponId) {
        return couponRepository.findById(couponId);
    }

    @Override
    public void saveEditCoupon(CouponDto couponDto) {
        Optional<Coupon>couponOptional=couponRepository.findById(couponDto.getId());
        Coupon coupon=couponOptional.get();
        coupon.setMinimumAmount(couponDto.getMinimumAmount());
        coupon.setExpatriationDate(couponDto.getExpatriationDate());
        coupon.setDiscountAmount(couponDto.getDiscountAmount());
        coupon.setDescription(couponDto.getDescription());
        coupon.setCouponCod(couponDto.getCouponCod().toUpperCase());
        couponRepository.save(coupon);
    }
}
