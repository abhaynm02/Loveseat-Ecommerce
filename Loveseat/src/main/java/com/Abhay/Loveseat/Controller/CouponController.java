package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.CouponDto;
import com.Abhay.Loveseat.Dto.JsonInput;
import com.Abhay.Loveseat.Model.Coupon;
import com.Abhay.Loveseat.Service.CouponServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class CouponController {
    @Autowired
    private CouponServiceI couponServiceI;
    @GetMapping("/admin/listAllCoupons")
    public String listAllCoupons(@RequestParam(defaultValue = "0")int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model){
        Pageable pageable= PageRequest.of(page,size);
        Page<Coupon> coupons=couponServiceI.findAll(pageable);
        model.addAttribute("coupons",coupons);
        return "adminT/coupons";
    }
    @GetMapping("/admin/addCoupons")
    public String addCoupons(Model model){
        CouponDto couponDto=new CouponDto();
        model.addAttribute("coupon",couponDto);
        return "adminT/AddCoupons";
    }
    @PostMapping("/addCoupon")
    public String addCoupon(@Valid @ModelAttribute("coupon") CouponDto couponDto, BindingResult result,
                            RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "/admin/addCoupons";
        }
        try {
            couponServiceI.saveCoupon(couponDto);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","The coupon cod is already exists");

            return "redirect:/admin/addCoupons";
        }
        redirectAttributes.addFlashAttribute("success","coupon add successfully");
        return "redirect:/admin/listAllCoupons";
    }
    @PostMapping("/admin/unListCoupon")
    public ResponseEntity<String>unListCoupon(@RequestBody JsonInput jsonInput){
        long couponId= jsonInput.getCouponId();
        System.out.println(couponId);
        couponServiceI.unListCoupon(couponId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/admin/listCoupon")
    public ResponseEntity<String>listCoupon(@RequestBody JsonInput jsonInput){
        long couponId= jsonInput.getCouponId();
        couponServiceI.listCoupon(couponId);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/admin/edit-coupon/{couponId}")
    public ResponseEntity<?>editCoupon(@PathVariable long couponId){
        System.out.println(couponId);
        Optional<Coupon> coupon=couponServiceI.findById(couponId);
        Coupon coupon1=coupon.get();
        return new ResponseEntity<>(coupon1,HttpStatus.OK);
    }
   @PostMapping("/admin/saveEdit")
    public ResponseEntity<?>saveEdit(@RequestBody CouponDto couponDto){

        try {
            couponServiceI.saveEditCoupon(couponDto);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       System.out.println(couponDto);
        return new ResponseEntity<>(HttpStatus.OK);
   }

}
