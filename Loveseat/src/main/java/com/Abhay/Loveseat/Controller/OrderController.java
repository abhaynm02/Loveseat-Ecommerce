package com.Abhay.Loveseat.Controller;

import com.Abhay.Loveseat.Dto.JsonInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {
    @GetMapping("/home/order-Success")
    public String orderSuccess(){
        return "home/thankyou";
    }
    @PostMapping("/order")
    public String placeOrder(@RequestBody JsonInput jsonInput){
        int addressId=(int)jsonInput.getAddressId();
        System.out.println(addressId);
        return "redirect:/home/order-Success";
    }
}
