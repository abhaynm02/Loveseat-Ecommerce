package com.Abhay.Loveseat.Controller.Razorpay;

import com.Abhay.Loveseat.Dto.OrderRequest;
import com.Abhay.Loveseat.Dto.OrderResponse;
import com.Abhay.Loveseat.Dto.PaymentSuccessResponse;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Service.UserServiceI;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RazorPayController {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private razorPayService razorpayService;
    @Value("${razorpay.keyId}")
    private String keyId;
    @Value("${razorpay.keySecret}")
    private  String keySecret;
    @PostMapping("/online-payment")
    ResponseEntity<?>createOrder(@RequestBody OrderRequest orderRequest, Principal principal) throws RazorpayException {
        String email= principal.getName();
        UserEntity user= userServiceI.findByEmail(email);
        orderRequest.setAmount(user.getCart().getTotalPrice());

         OrderResponse order =razorpayService.createOrder(orderRequest);

        System.out.println(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @PostMapping("/verify-payment")
    ResponseEntity<?> verifyOrder(@RequestBody PaymentSuccessResponse paymentSuccessResponse) throws RazorpayException {
        String orderId= paymentSuccessResponse.getRazorpay_order_id();
        String paymentId= paymentSuccessResponse.getRazorpay_payment_id();
        String signature=paymentSuccessResponse.getRazorpay_signature();
        JSONObject options= new JSONObject();
        options.put("razorpay_order_id",orderId);
        options.put("razorpay_payment_id",paymentId);
        options.put("razorpay_signature",signature);
        boolean status= Utils.verifyPaymentSignature(options,keySecret);
        if (status){
            return  new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


}
