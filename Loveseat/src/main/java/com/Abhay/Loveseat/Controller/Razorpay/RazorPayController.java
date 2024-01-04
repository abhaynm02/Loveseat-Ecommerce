package com.Abhay.Loveseat.Controller.Razorpay;

import com.Abhay.Loveseat.Dto.OrderRequest;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.UserServiceI;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class RazorPayController {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private razorPayService razorpayService;
    @PostMapping("/online-payment")
    ResponseEntity<?>createOrder(@RequestBody OrderRequest orderRequest, Principal principal) throws RazorpayException {
        String email= principal.getName();
        UserEntity user= userServiceI.findByEmail(email);
        orderRequest.setAmount(user.getCart().getTotalPrice());
        String  orderId=razorpayService.createOrder(orderRequest);
        System.out.println(orderId);

        return new ResponseEntity<>("d", HttpStatus.CREATED);
    }

}
