package com.Abhay.Loveseat.Controller.Razorpay;

import com.Abhay.Loveseat.Dto.OrderRequest;
import com.Abhay.Loveseat.Dto.OrderResponse;
import com.Abhay.Loveseat.Service.CartServiceI;
import com.Abhay.Loveseat.Service.CouponServiceI;
import com.Abhay.Loveseat.Service.UserServiceI;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class razorPayService {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
     private CartServiceI cartServiceI;
    @Autowired
     private CouponServiceI couponServiceI;
    @Value("${razorpay.keyId}")
    private String keyId;
    @Value("${razorpay.keySecret}")
    private  String keySecret;


    public OrderResponse createOrder(OrderRequest orderRequest) throws RazorpayException {

        RazorpayClient razorpayClient= new RazorpayClient(keyId,keySecret);
        JSONObject orderRequestObject= new JSONObject();
        double totalAmount= orderRequest.getAmount();
        long couponId= orderRequest.getCouponId();
        if (couponId !=0){
            totalAmount=couponServiceI.calculateCouponDiscount(couponId,orderRequest.getAmount());
        }
        orderRequestObject.put("amount",totalAmount*100);
        orderRequestObject.put("currency","INR");
        Order order;
        order = razorpayClient.orders.create(orderRequestObject);
        OrderResponse orderResponse =new OrderResponse();
        orderResponse.setAmount(order.get("amount"));
        orderResponse.setOrderId(order.get("id"));

        return  orderResponse;
    }
}
