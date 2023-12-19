package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.JsonInput;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.OrderServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class OrderController {
    @Autowired
    private OrderServiceI orderServiceI;
    @Autowired
    private UserServiceI userServiceI;
    @GetMapping("/home/order-Success")
    public String orderSuccess(){
        return "home/thankyou";
    }
    @PostMapping("/order")
    public String placeOrder(@RequestBody JsonInput jsonInput , Principal principal){
         String email=principal.getName();
        UserEntity user=userServiceI.findByEmail(email);
        long addressId=(long)jsonInput.getAddressId();
        orderServiceI.placeOrder(addressId,user);


        System.out.println(addressId);
        return "redirect:/home/order-Success";
    }
}
